/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class TransactionManagerHandler implements TransactionManager.Iface {
	
	private final Object lock = new Object();
	private String filename;
	private static int timeout = 10000;
	
	// Log transaction in "Participant-log" for replay on recovery
	private void logTransaction(Transaction transaction, String status) {
        synchronized(lock) {
        	Logger.logTransaction(transaction, "", status, filename);
        }
    }
	
	// Append date to "Participant-log" file
	public void setParticipant(String participant){
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		this.filename = "Participant-log-" + participant + "-" + date;
	}
	
	// Check for pending transactions("VOTE_COMMIT" or "VOTE_ABORT") in "Participant-log" file 
	// If no pending transaction found call doCommit method else call doAbort method for that specific transaction
	public boolean startRecovery(String cordinatorInfo) throws SystemException, TException{
		TTransport transport = null;
		boolean isRecovered = false;
		try{
			String [] cordinator = cordinatorInfo.split(" ");
			transport = new TSocket(cordinator[0], Integer.valueOf(cordinator[1]), timeout);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			FileStore.Client client = new FileStore.Client(protocol);
			ArrayList<Transaction> transactions = Logger.getAllPendingTransaction(filename);
			for(Transaction transaction : transactions ){
				if(! client.isGlobalComit(transaction)){
					doAbort(transaction);
					System.out.println("GLOBAL_ABORT");
				}
				else {
					doCommit(transaction);
					System.out.println("GLOBAL_COMMIT");
				}
				isRecovered = true;
			}			
		} 
		catch (Exception ex) {	
			isRecovered = false;
		}  finally {
			transport.close();
		}
		return isRecovered;
	}
	
	@Override
	// If a participant receives a read request then read the content from the file and log "SUCCESS" / "FAILURE"
	public Transaction readOperation(Transaction transaction) throws SystemException, TException {
		String content = null;
		try {
			Path path = Paths.get("files/" +  transaction.getFilename());	
			while(content == null){
				content = FileOperation.readContent(path, "read");
			}
			transaction.setContent(content);
			transaction.setSuccess(true);
			logTransaction(transaction, "SUCCESS");

		} catch (IOException ex) {
			transaction.setSuccess(false);
			logTransaction(transaction, "FAILURE");
			System.out.println(transaction.transactionId
					+ " : Failed to read file " + transaction.filename);
		}
		return transaction;
	}	

	@Override
	// When a participant receives a "Can Commit?" request and if there are no conflicting transaction (write/delete on same file) then it vote "yes" and log "VOTE_COMMIT"
	// If there are conflicting transaction (write/delete on same file) then it vote "No" and log "VOTE_ABORT"
	public boolean canCommit(Transaction transaction) throws SystemException, TException {
		synchronized (lock) {
			boolean isWaiting = Logger.isPendingUpdate(filename, transaction);
			
			//Manually added Sleep of 5 second to test Coordinator failure
			/*try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			}*/
			
			if (isWaiting) {
				// Abort current Transaction
				transaction.setReady(false);
				logTransaction(transaction, "VOTE_ABORT");
				return false;
			}
			transaction.setReady(true);
			logTransaction(transaction, "VOTE_COMMIT");
			return true;
		}
	}

	@Override
	//Read transaction from "Participant-log" file and if it is a write request then write the content to file
	// If it is a delete request then delete file
	public void doCommit(Transaction transaction) throws SystemException, TException {
		boolean isSuccess = false;
		String content = null;
		try {
			Path path = Paths.get("files/" + transaction.getFilename());
			transaction = Logger.retrieveTransaction(transaction.getTransactionId(), filename);
			// To handle explicit abort
			Thread.sleep(2000);
			switch(transaction.getOperation().toLowerCase()){
			case "write":
				while (!isSuccess) {
					isSuccess = FileOperation.writeContent(transaction);
				}
				break;
			case "delete":
				while (content == null) {
					transaction.setContent(FileOperation.readContent(path, "delete"));
				}
				break;
			}
			transaction.setCommitted(true);
			logTransaction(transaction, "GLOBAL_COMMIT");

		} catch (InterruptedException ex) {

		} catch (IOException ex) {
			transaction.setCommitted(false);
			logTransaction(transaction, "FAILURE");
			System.out.println(transaction.transactionId
					+ " : Failed to commit file " + transaction.filename);
		}
	}

	@Override
	// Log "GLOBAL_ABORT" in "Participant-log" file 
	public void doAbort(Transaction transaction) throws SystemException,	TException {
		transaction.setAborted(true);
		logTransaction(transaction, "GLOBAL_ABORT");
	}
}