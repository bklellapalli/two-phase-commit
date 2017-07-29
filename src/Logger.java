/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Logger {

	// Load XML document and parse it into DOM.
	private static Document createInstance(String filename) throws ParserConfigurationException, SAXException, IOException {
		File xmlFile = new File(filename);
		// Create Document Builder Factory
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		 // Create Document Builder
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		// open and parse XML-file
		Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		return doc;
	}

	// Update XML file
	private static void updateStream(Document doc, String filename) throws TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		StreamResult result = new StreamResult(new File(filename));
		transformer.transform(source, result);
	}

	// Create attribute for an element in XML file
	private static void createAttribute(Document doc, Element element, String attribute, String value) {
		Attr attr = doc.createAttribute(attribute);
		attr.setValue(value);
		element.setAttributeNode(attr);
	}

	// Create a new log file that contain Transaction id, operation, file name, content, participant process and status of transaction
	private static void createLogFile(Transaction transaction, String participant, String status, String filename)
			throws ParserConfigurationException, TransformerException {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("TransactionLog");
		doc.appendChild(rootElement);

		Element element = doc.createElement("Transaction");
		rootElement.appendChild(element);

		createAttribute(doc, element, "ID", transaction.getTransactionId());
		createAttribute(doc, element, "operation", transaction.getOperation());
		createAttribute(doc, element, "filename", transaction.getFilename());
		createAttribute(doc, element, "content", transaction.getContent());
		createAttribute(doc, element, "participant", participant);
		createAttribute(doc, element, "status", status);

		updateStream(doc, filename);
	}
	// Add transaction that contain Transaction id, operation, file name, content, participant process and status of transaction to existing log file
	private static void addElement(Transaction transaction, String participant, String status, String filename)
			throws ParserConfigurationException, TransformerException, SAXException, IOException {

		Document doc = createInstance(filename);
		NodeList nodeList = doc.getElementsByTagName("TransactionLog");
		Element trans = (Element) nodeList.item(nodeList.getLength() - 1);
	
		Element element = doc.createElement("Transaction");
		createAttribute(doc, element, "ID", transaction.getTransactionId());
		createAttribute(doc, element, "operation", transaction.getOperation());
		createAttribute(doc, element, "filename", transaction.getFilename());
		createAttribute(doc, element, "content", transaction.getContent());
		createAttribute(doc, element, "participant", participant);
		createAttribute(doc, element, "status", status);
		
		trans.appendChild(element);
		doc.getDocumentElement().normalize();
		updateStream(doc, filename);
	}

	// Add Log Transaction details in log file (Create new or add to existing log file) - 
	// Called from coordinator/ Participant (replay log on recovery)
	public static void logTransaction(Transaction transaction, String participant ,String status, String filename) {
		try {
			File file = new File(filename);
			if (file.exists() && !file.isDirectory()) {
				addElement(transaction, participant, status, filename);
			} else {
				createLogFile(transaction, participant, status, filename);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// Search log file for "transaction id" and returns transaction that matches the id
	public static Transaction retrieveTransaction(String transactionId, String filename) {
		try {
			Document doc = createInstance(filename);
			NodeList nodeList = doc.getElementsByTagName("Transaction");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element trans = (Element) nodeList.item(i);
				if (trans.getAttribute("ID").equals(transactionId)) {
					// If match for transaction found
					return new Transaction().setTransactionId(transactionId)
							.setOperation(trans.getAttribute("operation"))
							.setFilename(trans.getAttribute("filename"))
							.setParticipantInfo(trans.getAttribute("participant"))
							.setContent(trans.getAttribute("content"));
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	// Check if any transaction has pending update ("VOTE_COMMIT" or "VOTE_ABORT" status in log file)
	public static boolean isPendingUpdate(String filename, Transaction transaction) {
		try {
			Document doc = createInstance(filename);
			NodeList nodeList = doc.getElementsByTagName("Transaction");
			for (int i = nodeList.getLength() - 1; i >= 0; i--) {
				Element trans = (Element) nodeList.item(i);	
				if(trans.getAttribute("filename").equals(transaction.getFilename())){
					
					if (trans.getAttribute("status").equals("VOTE_COMMIT") || 
							trans.getAttribute("status").equals("VOTE_ABORT")){
						// Pending update transaction
						return true;
					
					} else if (trans.getAttribute("status").equals("GLOBAL_COMMIT") || 
							trans.getAttribute("status").equals("GLOBAL_ABORT")){
						//Global committed transaction
						return false;	
					} 
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	// Return all transactions that has "VOTE_COMMIT" or "VOTE_ABORT" status
	public static ArrayList<Transaction> getAllPendingTransaction(String filename) {
		ArrayList<Transaction> transactionPending = new ArrayList<Transaction>();
		try {
			Document doc = createInstance(filename);
			NodeList nodeList = doc.getElementsByTagName("Transaction");
			for (int i = nodeList.getLength() - 1; i >= 0; i--) {
				Element trans = (Element) nodeList.item(i);
				if (trans.getAttribute("status").equals("VOTE_COMMIT")
						|| trans.getAttribute("status").equals("VOTE_ABORT")) {
					// Transaction that has pending update
					Transaction t = new Transaction()
							.setTransactionId(trans.getAttribute("ID"))
							.setOperation(trans.getAttribute("operation"))
							.setFilename(trans.getAttribute("filename"))
							.setParticipantInfo(
									trans.getAttribute("participant"))
							.setContent(trans.getAttribute("content"));
					transactionPending.add(t);
				} else if (trans.getAttribute("status").equals("GLOBAL_COMMIT")
						|| trans.getAttribute("status").equals("GLOBAL_ABORT")) {
					for (Transaction t : transactionPending) {
						if (t.getTransactionId().equals(trans.getAttribute("ID"))){
							System.out.println(t .getTransactionId() +"removing transactionPending");
							transactionPending.remove(t);
							System.out.println(t .getTransactionId() +"removed transactionPending");
						}
					}

				}
			}
		} catch (Exception ex) {
			//System.out.println(ex.getMessage());
		}
		return transactionPending;
	}

	// Check if transaction has been committed globally ("GLOBAL_COMMIT") or is there any pending transaction
	public static boolean isGlobalCommitted(String transactionId, String filename) {
		try {
			Document doc = createInstance(filename);
			NodeList nodeList = doc.getElementsByTagName("Transaction");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element trans = (Element) nodeList.item(i);
				if (trans.getAttribute("ID").equals(transactionId) && 
						trans.getAttribute("status").equals("GLOBAL_COMMIT")) {
					// Transaction that has "GLOBAL_COMMIT" status
					return true;
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
}