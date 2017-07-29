/**
 * @author Balakrishna Lellapalli
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import ThriftService.*;

public class FileServerHandler implements FileStore.Iface {
	List<RFile> rFileList = new ArrayList<RFile>();
		
	//Add meta data information to rFile and contet to rFile
	private RFile addCurrentFiles(File file) throws IOException, NoSuchAlgorithmException {
		RFile rFile = new RFile();
		BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		FileOwnerAttributeView ownerAttr = Files.getFileAttributeView(file.toPath(), FileOwnerAttributeView.class);
		String content = getContent(file.getName());
		RFileMetadata metaData = new RFileMetadata();
		metaData.setFilename(file.getName());
		metaData.setCreated(attr.creationTime().toMillis());
		metaData.setUpdated(attr.lastModifiedTime().toMillis());
		metaData.setVersion(0);
		metaData.setOwner(ownerAttr.getOwner().getName());
		metaData.setContentLength(content.length());
		metaData.setContentHash(getmd5Hash(content));
		rFile.setContent(content);
		rFile.setMeta(metaData);
		return rFile;
	}
	
	// Generate MD5 Hash value of the content passed as argument of the method
	private String getmd5Hash(String content) throws NoSuchAlgorithmException {
		byte[] contentInBytes = content.getBytes();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] mdbytes = md5.digest(contentInBytes);
		
		//convert the byte to hex format method
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
          sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }	
		return sb.toString();
	}
	
	// Read content from file
	private String getContent(String fileName) throws IOException {
		String line = null;
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		while ((line = bufferedReader.readLine()) != null)
			stringBuffer.append(line).append("\n");
		bufferedReader.close();
		return stringBuffer.toString();
	}
	
	// Write content to file
	private void writeContent(RFile rFile) throws IOException {		
		FileWriter writer = new FileWriter(".//" + rFile.getMeta().filename);
    	writer.write(rFile.content);
    	writer.close();	
	}
	
	@Override
	//Given an user name, all files owned by the user should be listed. If the user does not exist, then an
	//exception should be thrown
	public List<RFileMetadata> listOwnedFiles(String user) throws SystemException {
		List<RFileMetadata> rFileMetaData = new ArrayList<RFileMetadata>();
		for (RFile rFile : rFileList) {
			if (user.equals(rFile.getMeta().owner))
				rFileMetaData.add(rFile.getMeta());
		}
		if(rFileMetaData.isEmpty())
			throw new SystemException().setMessage("User " + user +" does not exist."); 
		
		return rFileMetaData;
	}

	@Override
	//Given a name, owner, and contents, the corresponding file should be written to the server. Meta-
	//information, including the filename, creation-time, update-time, version (start from 0), owner, content length,
	//and content hash (use the MD5 hash) should also be stored at the server side. If the filename does not exist on
	//the server, a new file should be created with its version attribute set to 0. Otherwise, the file contents should
	//be overwritten and the version number should be incremented.
	public StatusReport writeFile(RFile rFile) throws SystemException {
		StatusReport status = new StatusReport();
		try{
			for (RFile fileInfo : rFileList) {
				if (fileInfo.getMeta().filename.equals(rFile.getMeta().filename)) {
					if (fileInfo.getMeta().owner.equals(rFile.getMeta().owner)) {
						writeContent(rFile);
						RFile tempFile = addCurrentFiles(new File(rFile.getMeta().filename));
						fileInfo.setContent(tempFile.content);
						fileInfo.getMeta().setUpdated(tempFile.getMeta().updated);
						fileInfo.getMeta().setContentLength(tempFile.getMeta().contentLength);
						fileInfo.getMeta().setContentHash(tempFile.getMeta().contentHash);
						fileInfo.getMeta().setVersion(fileInfo.getMeta().version + 1);
						status.setStatus(Status.SUCCESSFUL);
						return status;
					}
					else{
						status.setStatus(Status.FAILED);
						return status;
					}
				}
			}
			writeContent(rFile);
			RFile fileInfo = addCurrentFiles(new File(rFile.getMeta().filename));
			fileInfo.getMeta().setOwner(rFile.getMeta().owner);
			rFileList.add(fileInfo);
			status.setStatus(Status.SUCCESSFUL);
		}
		catch(IOException | NoSuchAlgorithmException e){
			System.out.println(e.getMessage());
			}
		return status;
	}

	@Override
	//If a file with a given name and owner exists on the server, both the contents and meta-information should
	//be returned. Otherwise, an exception should be thrown.
	public RFile readFile(String filename, String owner) throws SystemException {
		for (RFile rFile : rFileList) {
			RFileMetadata metaData = rFile.getMeta();
			if (owner.equals(metaData.owner) && filename.equals(metaData.filename))
				return rFile;
		}
		throw new SystemException().setMessage(
				"File " + filename + " for user " + owner +" does not exist");
	}
}
