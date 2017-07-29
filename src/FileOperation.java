/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.OverlappingFileLockException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileOperation {

	// Read content from file in case of "read" operation and delete file in case of "delete" operation 
	public static String readContent(Path path, String operation) throws IOException {
		FileChannel fileChannel = null;
		String content = null;
		try {
			switch (operation) {
			case "delete":
				fileChannel = FileChannel.open(path, StandardOpenOption.READ,
						StandardOpenOption.DELETE_ON_CLOSE);
				break;
			case "read":
				fileChannel = FileChannel.open(path, StandardOpenOption.READ);
				break;
			}
			fileChannel.lock(0, Long.MAX_VALUE, true);
			StringBuilder sb = new StringBuilder();
			ByteBuffer buffer = ByteBuffer.allocate(20);
			int noOfBytesRead = fileChannel.read(buffer);
			while (noOfBytesRead != -1) {
				buffer.flip();
				while (buffer.hasRemaining()) {
					sb.append((char) buffer.get());
				}
				buffer.clear();
				noOfBytesRead = fileChannel.read(buffer);
			}
			content = sb.toString();
		} catch (OverlappingFileLockException ex) {
			//Ignore Exception
		} finally {
			if (fileChannel != null)
				fileChannel.close();
		}
		return content;
	}
	
	//Open FileChannel and write transaction's content 
	public static boolean writeContent(Transaction transaction) throws IOException {
		boolean isSuccess = false;
		FileChannel fileChannel = null;
		try {
			ByteBuffer buffer = ByteBuffer.wrap(transaction.content.getBytes());
			Path path = Paths.get("files/" + transaction.getFilename());
			fileChannel = FileChannel.open(path,StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			fileChannel.position(0);
			fileChannel.lock();
			fileChannel.write(buffer);
			isSuccess = true;

		} catch (OverlappingFileLockException ex) {
			//Ignore Exception
		} finally {
			if (fileChannel != null)
				fileChannel.close();
		}
		return isSuccess;
	}
}
