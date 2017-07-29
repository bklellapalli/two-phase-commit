/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Parser {

	// Queue holds all transaction from Transaction file provided as input
	private static LinkedList<Transaction> transactions = new LinkedList<Transaction>();

	// Parse Transaction from "fileOperations" file and add in Queue<Transaction>
	public static LinkedList<Transaction> parseTransaction(String clientId,	String filePath) throws IOException {
		String line = null;
		String targetFileName = null;
		String content = null;
		int count = 0;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
			while ((line = bufferedReader.readLine()) != null) {
				String transactionId = clientId + count++;
				String operation = line.substring(0, line.indexOf("("));
				if (operation.equalsIgnoreCase("write")) {
					targetFileName = line.substring(line.indexOf("(") + 1, line.indexOf(","));
					content = line.substring(line.indexOf(",") + 1, line.lastIndexOf(")"));
				} else {
					targetFileName = line.substring(line.indexOf("(") + 1, line.indexOf(")"));
				}
				Transaction transaction = new Transaction()
				.setTransactionId(transactionId)
				.setOperation(operation).setFilename(targetFileName)
				.setContent(content);

				transactions.add(transaction);
			}
		} finally {
			bufferedReader.close();
		}
		return transactions;
	}
}