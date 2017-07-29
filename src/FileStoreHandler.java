/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class FileStoreHandler implements FileStore.Iface {

	private static int timeout = 10000;
	private final Object lock = new Object();
	private static ArrayList<String> participantsList = new ArrayList<String>();
	
	// Log transaction in "Cordinator-log" for replay on recovery
	private void logTransaction(Transaction transaction, String participants, String status) {
        synchronized(lock) {
        	String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        	Logger.logTransaction(transaction, participants, status, "Cordinator-log-"+ date);
        }
    }

	// Sends request to participant that if participant "can commit" the transaction (passed as parameter)
	private static boolean sendVoteRequestToParticipants(Transaction transaction, String participants) 
			throws SystemException, TException{
		TTransport transport = null;
		String [] participant = participants.split(" ");
		boolean voteCommit = false;
		try {
			transport = new TSocket(participant[1],Integer.valueOf(participant[2]), timeout);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			TransactionManager.Client client = new TransactionManager.Client(protocol);
			voteCommit = client.canCommit(transaction);
			
		} catch(TTransportException ex){
			//TODO: Handle TimeOut Exception (If Timeout send Global abort to all and log)
			System.out.println(ex.getMessage());
			
		} finally {
			transport.close();	
		}
		return voteCommit;
	}
	
	// Log "PREPARE" status for transaction and then send vote request to participant
	// if vote received == "yes" then log VOTE_COMMIT in "Cordinator-log" file else log "VOTE_ABORT" in "Cordinator-log" file
	private ArrayList<String> voteRequest(Transaction transaction) throws SystemException, TException{
		ArrayList<String> readyParticipant = new ArrayList<String>();
		for(String participants : participantsList){
			logTransaction(transaction, participants, "PREPARE");

			if(sendVoteRequestToParticipants(transaction, participants)){
				logTransaction(transaction, participants, "VOTE_COMMIT");
				if(!readyParticipant.contains(participants))
					readyParticipant.add(participants);
			} else {
				logTransaction(transaction, participants, "VOTE_ABORT");
			}
		}
		return readyParticipant;
	}
	
	// Send "commit/ abort" request to participant based on response from participant for "Can Commit?" request
	private void sendCommitRequest(Transaction transaction, String participants, String status) 
			throws SystemException, TException{
		
		TTransport transport = null;
		String [] participant = participants.split(" ");
		try {
			transport = new TSocket(participant[1],Integer.valueOf(participant[2]));
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			TransactionManager.Client client = new TransactionManager.Client(protocol);
			
			if(status.equals("GLOBAL_COMMIT"))
				client.doCommit(transaction);
			
			else if (status.equals("GLOBAL_ABORT"))
				client.doAbort(transaction);
			
		} finally {
			transport.close();	
		}
	}
	
	//1. Log "GLOBAL_COMMIT" for transaction in "Cordinator-log" if vote received == "yes" from all participant then 
	// send commit request for the transaction to all participant
	//2. Log "GLOBAL_ABORT" for transaction in "Cordinator-log" if vote received == "No" from any of the participant then 
	// send abort request for the transaction to all participant that has voted yes
	private Transaction commitRequest(Transaction transaction, ArrayList<String> readyParticipant) 
			throws SystemException, TException {
		
		//Manually added Sleep of 5 second to test Coordinator failure
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		
		if(readyParticipant.size() < participantsList.size()){
			logTransaction(transaction, "", "GLOBAL_ABORT");
			for(String participants : readyParticipant){	
				sendCommitRequest(transaction, participants, "GLOBAL_ABORT");
				transaction.setCommitted(false);
			}	
		} else {
			logTransaction(transaction, "", "GLOBAL_COMMIT");
			for(String participants : participantsList){
				sendCommitRequest(transaction, participants, "GLOBAL_COMMIT");
				transaction.setCommitted(true);
			}
		}
		readyParticipant.clear();
		return transaction;
	}
	
	// Add all participants to the participant list from "serverInfo" file
	public void setParticipantList(String fileName) throws IOException{
		String line = null;
		BufferedReader bufferedReader = null;
		try{
			bufferedReader = new BufferedReader(new FileReader(fileName));
			while ((line = bufferedReader.readLine()) != null){		
				participantsList.add(line);
			}
		}finally{
			bufferedReader.close();	
		}
	}
	
	@Override
	// Randomly select any of the participant from the participant list and send read operation request for the transaction
	// Log "SUCCESS" / "FAILURE" in "Cordinator-log" file, if transaction succeed / failed.
	public Transaction readFile(Transaction transaction) throws SystemException, TException {
		TTransport transport = null;
		int index = new Random().nextInt(participantsList.size());
		String [] participant = participantsList.get(index).split(" ");
		try{
			transport = new TSocket(participant[1],Integer.valueOf(participant[2]),timeout);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			TransactionManager.Client client = new TransactionManager.Client(protocol);
			transaction = client.readOperation(transaction);	
			//Log Read Operation
			if(transaction.isSuccess()) {
				logTransaction(transaction, participantsList.get(index), "SUCCESS");
			}
			else {
				System.out.println(transaction.transactionId
						+ " : Failed to read file " + transaction.filename);
				logTransaction(transaction, participantsList.get(index), "FAILURE");
			}
		} catch(TTransportException ex){
			logTransaction(transaction, participantsList.get(index), "FAILURE");
			System.out.println(ex.getMessage());
		} finally{
			transport.close();
		}
		return transaction;
	}
	
	@Override
	// Send commit request to all participants (write file)
	public Transaction writeFile(Transaction transaction) throws SystemException, TException {
		ArrayList<String> readyParticipant = voteRequest(transaction);
		transaction = commitRequest(transaction, readyParticipant);
		return transaction;
	}

	@Override
	// Send commit request to all participants (delete file)
	public Transaction deleteFile(Transaction transaction) throws SystemException, TException {
		ArrayList<String> readyParticipant = voteRequest(transaction);
		transaction = commitRequest(transaction, readyParticipant);
		return transaction;
	}

	@Override
	// Check "Cordinator-log" file to find if there is GLOBAL_COMMIT for the transaction passed as parameter
	public boolean isGlobalComit(Transaction transactions) throws SystemException, TException {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return Logger.isGlobalCommitted(transactions.getTransactionId(), "Cordinator-log-"+ date);
	}	
}