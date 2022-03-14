package com.chirag.cloud.azure.service.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import com.azure.core.util.BinaryData;
import com.azure.identity.DefaultAzureCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobProperties;

@Service
public class BlobStorageService {
	
	private BlobServiceClient blobServiceClient;
	private BlobContainerClient blobContainerClient;
	
	private final String managedIdentityClientId = "<MagedIdentityClientId>";
	
	private final String endPoint="https://<HostName>/";
	//From "Endpoints" -> "BlobService"
	
	
	private String connectionString ="DefaultEndpointsProtocol=https;AccountName=<StorageAccountName>;AccountKey=<accesskey>;EndpointSuffix=<sufixData>";
	//Get connectionSTring from "Access keys"
	
	public BlobStorageService() {
		blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
		blobContainerClient = blobServiceClient.getBlobContainerClient("test-container");
	}

	public void BlobStorageService1() {
		//For managedIdentity
		
		//DefaultAzureCredential credential2 = new DefaultAzureCredentialBuilder().managedIdentityClientId(managedIdentityClientId).build();
		ManagedIdentityCredential credential = new ManagedIdentityCredentialBuilder().clientId(managedIdentityClientId).build();
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.credential(credential)
				.endpoint(endPoint)
				.buildClient();
		
		/*Second way
		 * BlobContainerClientBuilder blobContainerClientBuilder = new
		 * BlobContainerClientBuilder();
		 * blobContainerClientBuilder.credential(credential);
		 * blobContainerClientBuilder.endpoint(endPoint+<ContainerName>);
		 * 
		 * this.blobContainerClient = blobContainerClientBuilder.buildClient();
		 */
		
		this.blobContainerClient = blobServiceClient.getBlobContainerClient("test-container");
	}
	
	public String downloadData() {
		BlobClient blobClient = blobContainerClient.getBlobClient("test_file.txt");
		/*
		 * Path tempFile = Files.createTempFile("temp_file", ".txt"); FileOutputStream
		 * fileOutputStream = new FileOutputStream(tempFile.toFile());
		 */
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		BlobProperties properties = blobClient.getProperties();
		System.out.println(properties.getLastModified()+":"+properties.getBlobSize());
		//BinaryData downloadContent = blobClient.downloadContent(); 
		blobClient.downloadStream(bos);
		return bos.toString();
		//return downloadContent.toString();
	}
	
	public String uploadData() {
		if(blobContainerClient.exists()) {
			System.out.println("-------------Container is exisit");
		} else {
			System.out.println("-------------Container is not exisit");
			blobContainerClient.create();
		}
		String data = "Hello all datafor test";
		byte[] byteArray = data.getBytes();
		
		BlobClient blobClient = blobContainerClient.getBlobClient("new-test-blob");
		InputStream is = new ByteArrayInputStream(byteArray);
		blobClient.upload(is, data.length());
		try {
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("----------_File uploading done");
		return "Done";
	}
	
	public void copyFile(String sourceBucketName, String sourceFilePath, String destinationBucketName, String destinationFilePath) throws Exception {

		//BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
		
        BlobContainerClient sourceContainer = blobServiceClient.getBlobContainerClient(sourceBucketName); 
        BlobClient sourceBlob = sourceContainer.getBlobClient(sourceFilePath);

        BlobContainerClient destContainer = blobServiceClient.getBlobContainerClient(destinationBucketName); 
        BlobClient destBlob = destContainer.getBlobClient(destinationFilePath);
        
        destBlob.copyFromUrl(sourceBlob.getBlobUrl());

        
    }
	
	public void deleteFile(final String bucketName, final String fileNameOnStorage)
			throws Exception {
		BlobContainerClient container = blobServiceClient.getBlobContainerClient(bucketName);
		BlobClient blob = container.getBlobClient(fileNameOnStorage);

		if (blob.exists()) {
			blob.delete();
		}
	}

}
