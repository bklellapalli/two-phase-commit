/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TList;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import ThriftService.*;

public class JavaClient {
	
	// --operation [operation], where operation can be "read", "write", and "list", and should invoke the respective remote procedure call
	// --filename [filename], should be used as input to specify either a local file to be written to the remote system, or a
	// remote filename to be read (if the operation is “list”, then this argument should not be used)
	// --user [user], should specify the owner of the file for the current operation
	public static void main(String[] args) {
		String operation = "", fileName = "", user = "";
		String sampleInput = "./client.sh localhost 9090 --operation read --filename example.txt --user guest";
		if (args.length < 6) {
			System.out.println("Incorrect Format.\n Usage: " + sampleInput);
			System.exit(0);
		}
		try {
			TTransport transport = new TSocket(args[0], Integer.valueOf(args[1]));
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			FileStore.Client client = new FileStore.Client(protocol);
			
			for(int i = 2; i< args.length; i++ ){
				if(args[i].equals("--operation"))
					operation = args[i+1];
				else if(args[i].equals("--filename"))
					fileName = args[i+1];
				else if(args[i].equals("--user"))
					user = args[i+1];
			}
			switch (operation) {
			case "read":
				if(fileName== "" || user == ""){
					System.out.println("Incorrect Format.\n Usage: " + sampleInput);
					System.exit(0);
				}
				readFile(client, fileName, user);
				break;
			case "write":
				if(fileName== "" || user == ""){
					System.out.println("Incorrect Format.\n Usage: " + sampleInput);
					System.exit(0);
				}
				writeFile(client, fileName, user);
				break;
			case "list":
				if(user == ""){
					System.out.println("Incorrect Format.\n Usage: " + sampleInput);
					System.exit(0);
				}
				listFile(client, user);
				break;
			default:
				System.out.println("Incorrect Format.\n Usage: " + sampleInput);
				System.exit(0);
				break;
			}
			transport.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	// Read content from file name
	private static String getContent(String fileName) throws IOException {
		String line = null;
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		while ((line = bufferedReader.readLine()) != null)
			stringBuffer.append(line).append("\n");
		bufferedReader.close();
		return stringBuffer.toString();
	}
	
	// If a file with a given name and owner exists on the server, both the contents and meta-information should be returned
	//If any exceptions is thrown by the remote method, write JSON format to the standard output
	private static void readFile(FileStore.Client client, String fileName, String user) {
		TIOStreamTransport thriftTransport = new TIOStreamTransport(System.out);
		TJSONProtocol jsonProtocol = new TJSONProtocol(thriftTransport);
		try {
			RFile rfile = client.readFile(fileName, user);
			if (rfile.isSetContent()) {
				rfile.write(jsonProtocol);
			}
			thriftTransport.write("\n".getBytes());
		} catch (TException e) {
			try {
				((SystemException)e).write(jsonProtocol);
				thriftTransport.write("\n".getBytes());
			} catch (TException e1) {
				System.out.println(e1.getMessage());
			}
		} finally {
			thriftTransport.close();
		}
	}

	// Given a name, owner, and contents, the corresponding file should be written to the server.
	private static void writeFile(FileStore.Client client,String fileName, String user) throws TException, IOException {	
		RFileMetadata metaData = new RFileMetadata();
		RFile rFile = new RFile();
		metaData.setFilename(fileName);
		metaData.setOwner(user);
		rFile.setMeta(metaData);
		rFile.setContent(getContent(fileName));
		
		TIOStreamTransport thriftTransport = new TIOStreamTransport(System.out);
		TJSONProtocol jsonProtocol = new TJSONProtocol(thriftTransport);
		client.writeFile(rFile).write(jsonProtocol);
		thriftTransport.write("\n".getBytes());
		thriftTransport.close();
	}

	//Given an user name, all files owned by the user should be listed.
	//If any exceptions is thrown by the remote method, write JSON format to the standard output
	private static void listFile(FileStore.Client client, String user) {
		TIOStreamTransport thriftTransport = new TIOStreamTransport(System.out);
		TJSONProtocol jsonProtocol = new TJSONProtocol(thriftTransport);	
		try{
			List<RFileMetadata> metaDataList = client.listOwnedFiles(user);
			jsonProtocol.writeListBegin(new TList(TType.STRUCT, metaDataList.size()));
			for(RFileMetadata metaData: metaDataList){
				metaData.write(jsonProtocol);
			}
			jsonProtocol.writeListEnd();
			thriftTransport.write("\n".getBytes());

		}catch (TException e) {
			try {
				((SystemException)e).write(jsonProtocol);
				thriftTransport.write("\n".getBytes());
			} catch (TException e1) {
				System.out.println(e1.getMessage());
			}
		} finally {
			thriftTransport.close();
		}		
	}
}
