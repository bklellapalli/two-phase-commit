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

public class Coordinator {

	private static FileStoreHandler fileStorehandler;
	private static FileStore.Processor<FileStoreHandler> fileStoreprocessor;
	private static String coordinatorInfoFileName = "coordinatorInfo";
	private static int port;

	// Starts server thread
	private static void startServer(FileStore.Processor<FileStoreHandler> processor) {
		try {
			TServerTransport serverTransport = new TServerSocket(port);
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(
					serverTransport).processor(processor));
			System.out.println("Starting Cordinator process at Port " + port);
			server.serve();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// Get coordinator information from coordinatorInfoFileName ("coordinatorInfo")
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
	
	// INPUT: ./coordinator.sh
	//Example:  ./coordinator.sh 
	public static void main(String[] args) {
		try {
			fileStorehandler = new FileStoreHandler();
			fileStoreprocessor = new FileStore.Processor<FileStoreHandler>(fileStorehandler);
			String [] coordinatorInfo = getCoordinatorInfo().split(" ");
			port = Integer.valueOf(coordinatorInfo[1]);
			fileStorehandler.setParticipantList("serverInfo");

			// Server thread
			Runnable server = new Runnable() {
				public void run() {
					startServer(fileStoreprocessor);
				}
			};
			new Thread(server).start();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}