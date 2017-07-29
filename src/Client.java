/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {
	
	private static int timeout = 20000;
	private static String oprFileName = "fileOperations";
	private static String coordinatorInfoFileName = "coordinatorInfo";
	
	// Display transaction details
	private static void formatAndDisplay(Transaction transaction) {
		if (transaction.getOperation().equalsIgnoreCase("read")) {
			if (transaction.isSuccess()) {
				System.out.println("Transaction "
						+ transaction.getTransactionId() + " : "
						+ transaction.getOperation() + "("
						+ transaction.getFilename() + ","
						+ transaction.getContent() + ")");
			} else {
				System.out.println("Transaction "
						+ transaction.getTransactionId()
						+ " : FAILED TO READ");
			}
		} else {
			String action = transaction.isCommitted() ? "GLOBAL_COMMIT" : "GLOBAL_ABORT";
			System.out.println("Transaction " + transaction.getTransactionId()
						+ " " + action + " : " + transaction.getOperation() + "("
						+ transaction.getFilename() + ")");
		}
	}
	
	// Coordinator information (IP address and port number)
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
	
	// INPUT Format: ./client.sh <clientId>
	//Example:  ./client.sh alpha 
	public static void main(String[] args) {
		TTransport transport = null;
		try {
			String [] coordinatorInfo = getCoordinatorInfo().split(" ");
			transport = new TSocket(coordinatorInfo[0], Integer.valueOf(coordinatorInfo[1]), timeout);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			FileStore.Client client = new FileStore.Client(protocol);

			//oprFileName ("fileOperations") contains file operation, file name and content (Example: write(A,Test Input 1))
			for(Transaction transaction : Parser.parseTransaction(args[0], oprFileName)){			
				switch (transaction.getOperation().toLowerCase()) {
				case "read": 
					formatAndDisplay(client.readFile(transaction));
					break;
				case "write": 
					formatAndDisplay(client.writeFile(transaction));
					break;
				case "delete":
					formatAndDisplay(client.deleteFile(transaction));
					break;
				default:
					System.out.println("Parsing Error!!!");
				}	
			}
		} catch (Exception ex) {
			System.out.println("No Response");
			//System.out.println(ex.getMessage());
		} finally {
			transport.close();
		}
	}
}