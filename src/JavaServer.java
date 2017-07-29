/**
 * @author Balakrishna Lellapalli
 *
 */
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import ThriftService.*;

public class JavaServer {

	public static FileServerHandler handler;
	public static FileStore.Processor<FileServerHandler> processor;
	public static int port;

	public static void main(String[] args) {
		try {
			handler = new FileServerHandler();
			processor = new FileStore.Processor<FileServerHandler>(handler);
			port = Integer.valueOf(args[0]);
			// Threads on which server runs
			Runnable server = new Runnable() {
				public void run() {
					startServer(processor);
				}
			};
			new Thread(server).start();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// Start Server
	public static void startServer(
			FileStore.Processor<FileServerHandler> processor) {
		try {
			TServerTransport serverTransport = new TServerSocket(port);
			TServer server = new TSimpleServer(
					new TServer.Args(serverTransport).processor(processor));
			System.out.println("Starting SERVER...");
			server.serve();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}