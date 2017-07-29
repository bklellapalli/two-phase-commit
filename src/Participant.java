/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class Participant {

	private static TransactionManagerHandler transactionManagerhandler;
	private static TransactionManager.Processor<TransactionManagerHandler> transactionManagerprocessor;
	private static String coordinatorInfoFileName = "coordinatorInfo";
	private static String serverInfoFileName = "serverInfo";

	// Starts server thread (listens)
	private static void startServer(TransactionManager.Processor<TransactionManagerHandler> processor, int port) {
		try {
			TServerTransport serverTransport = new TServerSocket(port);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
					serverTransport).processor(processor));
			System.out.println("Starting Participant process...!!!");
			server.serve();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	// Replay log on Participant recovery. If a participant fails then 
	//recovers two-phase commit implementation needs to recover and proceed as appropriate
	private static void startRecovery() {
		try {
			while (true) {
			Thread.sleep(1000);
			boolean isRecovered = transactionManagerhandler.startRecovery(getCoordinatorInfo());
			
			if(isRecovered){
				System.out.println("Recovery Complete");
				break;
				}
			}
		} catch (Exception ex) {
			System.out.println("Recovery Failed");
		}	
	}
	
	// Read coordinator information from file "coordinatorInfo"
	private static String getCoordinatorInfo() throws IOException{
		BufferedReader bufferedReader = null;
		String coordinatorInfo;
		try{
			bufferedReader = new BufferedReader(new FileReader(coordinatorInfoFileName));
			coordinatorInfo = bufferedReader.readLine();
		}
		finally {
			if(bufferedReader != null)
				bufferedReader.close();
		}
		return coordinatorInfo;
	}
	
	private static int setPort(String pName) throws IOException{
		String line = "";
		int portNo = 0;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(serverInfoFileName));
			while ((line = bufferedReader.readLine()) != null) {
				String [] participantInfo = line.split(" ");
				if(participantInfo[0].equals(pName)){
					portNo = Integer.valueOf(participantInfo[2]);
					break;
				}
			}
		}
		finally {
			if(bufferedReader != null)
				bufferedReader.close();
		}
		return portNo;
	}
	
	// INPUT: ./participant.sh <participant>
	// Sample: ./participant.sh p1
	public static void main(String[] args) {
		try {
			transactionManagerhandler = new TransactionManagerHandler();
			transactionManagerprocessor = new TransactionManager.Processor<TransactionManagerHandler>(transactionManagerhandler);
			transactionManagerhandler.setParticipant(args[0]);
			final int port = setPort(args[0]);
			
			// Server thread
			Runnable server = new Runnable() {
				public void run() {
					startServer(transactionManagerprocessor, port);
				}
			};
			new Thread(server).start();
			
			// Separate thread for recovery of participant process
			Runnable client = new Runnable() {
				public void run() {
					startRecovery();
				}
			};
			new Thread(client).start();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}