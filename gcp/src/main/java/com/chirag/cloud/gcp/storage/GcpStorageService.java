package com.chirag.cloud.gcp.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.omg.CORBA.SystemException;
import org.springframework.stereotype.Service;

import com.google.api.client.util.ByteStreams;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import com.google.cloud.storage.StorageOptions;
import com.google.common.collect.Lists;

@Service
public class GcpStorageService {

	private static final String gcpCredentialFilePath = "/home/chiragj/development/key/gcp_key/ezdi_integration.gcp.credential.json.erb";
	
	private static final Set<Integer> ERROR_CODE_TO_RETRY = new HashSet<Integer>(
			Arrays.asList(500, 502, 503, 429, 408));
	
	private Storage storage;
	
	
	
	public GcpStorageService() throws FileNotFoundException, IOException {
		super();
		storage = getGcpStorageService();
	}

	public Storage getGcpStorageService() throws FileNotFoundException, IOException {
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(gcpCredentialFilePath))
				.createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
		return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
	}
	
	public void uploadFile(String bucketName, String filePathOnStorage, String inputFilePath) throws IOException {

		File inputFile = new File(inputFilePath);
		InputStream inputStream = new FileInputStream(inputFile);
		
		try {
			Bucket bucket = storage.get(bucketName);
			bucket.create(filePathOnStorage, inputStream);
		} catch (StorageException e) {
			if (ERROR_CODE_TO_RETRY.contains(e.getCode())) {
				System.out.println("Retrying uploadFile...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}
				uploadFile(bucketName, filePathOnStorage, inputFilePath);
			} else {
				throw e;
			}
		} finally {
			inputStream.close();
		}
	}
	
	public void uploadForOcr(String bucketName, String filePathOnStorage, String inputFilePath) throws Exception {
		
		final String originalContentType = getFileType(inputFilePath);
		
		final File inputFile = new File(inputFilePath);
        BlobId ocrInputBlobId = BlobId.of(bucketName, filePathOnStorage);
        BlobInfo ocrInputBlobInfo = Blob.newBuilder(ocrInputBlobId).setContentType(originalContentType).build();
		/*
		 * try (WriteChannel writer = storage.writer(ocrInputBlobInfo)) {
		 * ByteStreams.copy(new FileInputStream(inputFile),
		 * Channels.newOutputStream(writer)); }
		 */
        WriteChannel writer = null;
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
        	writer = storage.writer(ocrInputBlobInfo);
            inputStream = new FileInputStream(inputFile);
            outputStream = Channels.newOutputStream(writer);
            ByteStreams.copy(inputStream, outputStream);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
			if(writer!=null) {
				writer.close();
			}
			if(inputStream!=null) {
				inputStream.close();
			}
			if(outputStream!=null) {
				outputStream.close();
			}
		}
	}
	
	 private static String getFileType(String filePath) throws Exception {
	        int extensionIdx = filePath.lastIndexOf('.');
	        String fileType = filePath.substring(extensionIdx);

	        switch (fileType) {
	            case ".tiff": {
	                return "image/tiff";
	            }
	            case ".pdf": {
	                return "application/pdf";
	            }
	            default:
	                throw new IllegalArgumentException("Does not support processing file type: " + fileType);
	        }
	    }
}
