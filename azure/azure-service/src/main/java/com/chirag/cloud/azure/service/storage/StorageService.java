package com.chirag.cloud.azure.service.storage;

import org.springframework.stereotype.Service;

@Service
public class StorageService {
	/*
	 * 
	 * public CloudBlobClient getCloudStorageClient() { CloudStorageAccount account
	 * = null; try { String connectionString
	 * ="DefaultEndpointsProtocol=https;AccountName=<StorageAccountName>;AccountKey=<accesskey>;EndpointSuffix=<sufixData>";
	 * account = CloudStorageAccount.parse(connectionString); } catch
	 * (InvalidKeyException | URISyntaxException e) { e.printStackTrace(); } return
	 * account.createCloudBlobClient(); }
	 * 
	 * 
	 * 
	 * public void downloadFile(final String containerName, final String
	 * fileNameOnStorage, final String downloadFilePath) throws Exception { String
	 * cloudPath = "KPMG/test1"; CloudBlobContainer container =
	 * getCloudStorageClient().getContainerReference(containerName); CloudBlockBlob
	 * blob = container.getBlockBlobReference(fileNameOnStorage);
	 * 
	 * Path path = Paths.get(downloadFilePath);
	 * Files.createDirectories(path.getParent());
	 * 
	 * FileOutputStream outputStream = new FileOutputStream(new
	 * File(downloadFilePath));
	 * 
	 * blob.download(outputStream); }
	 * 
	 * public String downloadData(final String bucketName, final String
	 * fileNameOnStorage) throws Exception {
	 * 
	 * 
	 * String cloudPath = ""; CloudBlobContainer container =
	 * getCloudStorageClient().getContainerReference(bucketName); CloudBlockBlob
	 * blob = container.getBlockBlobReference(cloudPath+fileNameOnStorage);
	 * 
	 * String data = blob.downloadText();
	 * 
	 * return data; }
	 * 
	 * public byte[] downloadByteData(final String bucketName, final String
	 * fileNameOnStorage) throws Exception { String cloudPath = "KPMG/test1/";
	 * CloudBlobContainer container =
	 * getCloudStorageClient().getContainerReference(bucketName); CloudBlockBlob
	 * blob = container.getBlockBlobReference(cloudPath+fileNameOnStorage);
	 * blob.downloadAttributes(); long fileByteLength =
	 * blob.getProperties().getLength(); byte[] fileContent = new
	 * byte[Math.toIntExact(fileByteLength)]; blob.downloadToByteArray(fileContent,
	 * 0);
	 * 
	 * return fileContent; }
	 * 
	 */}
