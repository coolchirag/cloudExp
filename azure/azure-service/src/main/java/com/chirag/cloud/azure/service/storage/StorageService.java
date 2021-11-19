package com.chirag.cloud.azure.service.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

import org.springframework.stereotype.Service;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

@Service
public class StorageService {

	public CloudBlobClient getCloudStorageClient() {
		CloudStorageAccount account = null;
		try {
			account = CloudStorageAccount.parse("DefaultEndpointsProtocol=https;AccountName=qacodingplatformsa;AccountKey=3QSj6nS5q3aZjm2CVI+TgnMv4fK39KYTe/WVplz8iOSmAJ/IONiqlF3Jog/GJK/YxbVH3GK5RT6BrQhgVjEOyg==;EndpointSuffix=core.usgovcloudapi.net");
		} catch (InvalidKeyException | URISyntaxException e) {
			e.printStackTrace();
		}
        return account.createCloudBlobClient();
	}
	
	public void downloadFile(final String containerName, final String fileNameOnStorage, final String downloadFilePath)
            throws Exception {
		String cloudPath = "inbound/KPMG/Test/pdftotext";
        CloudBlobContainer container = getCloudStorageClient().getContainerReference(containerName);
        CloudBlockBlob blob = container.getBlockBlobReference(fileNameOnStorage);

        Path path = Paths.get(downloadFilePath);
        Files.createDirectories(path.getParent());
        
        FileOutputStream outputStream = new FileOutputStream(new File(downloadFilePath));

        blob.download(outputStream);
    }
}
