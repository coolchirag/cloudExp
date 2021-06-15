package com.chirag.cloud.azure.computerVision;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionClient;
import com.microsoft.azure.cognitiveservices.vision.computervision.ComputerVisionManager;
import com.microsoft.azure.cognitiveservices.vision.computervision.implementation.ComputerVisionImpl;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.AnalyzeResults;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.Line;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.OperationStatusCodes;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ReadInStreamHeaders;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ReadOperationResult;
import com.microsoft.azure.cognitiveservices.vision.computervision.models.ReadResult;
import com.microsoft.rest.ServiceResponseWithHeaders;

import rx.Observable;
import rx.observables.BlockingObservable;

public class ComputerVisionQuickstart2 {

    static String subscriptionKey = "1a2e9e0922dc4df88873edec2f44107b";
    static String endpoint = "https://intezdicomputervision.cognitiveservices.azure.us/";
    
private static final Set<String> ALLOWED_FILE_FORMATES = new HashSet<String>();
	
	static {
		ALLOWED_FILE_FORMATES.add("JPEG");
		ALLOWED_FILE_FORMATES.add("PNG");
		ALLOWED_FILE_FORMATES.add("BMP");
		ALLOWED_FILE_FORMATES.add("PDF");
		ALLOWED_FILE_FORMATES.add("TIFF");
	}
    
    private static ComputerVisionClient compVisClient;
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("\nAzure Cognitive Services Computer Vision - Java Quickstart Sample");
        
     // Create an authenticated Computer Vision client.
        compVisClient = Authenticate(subscriptionKey, endpoint);
     // Read from local file
        String loc1 = ReadFromFile();
        
        //String loc2 = ReadFromFile();
        
        
        getAndPrintReadResult(loc1);
        System.out.println("---------------------Done1----------");
        //getAndPrintReadResult(loc2);
    }
    
    public static ComputerVisionClient Authenticate(String subscriptionKey, String endpoint){
        return ComputerVisionManager.authenticate(subscriptionKey).withEndpoint(endpoint);
    }
    
    /**
     * OCR with READ : Performs a Read Operation on a local image
     * @param client instantiated vision client
     * @param localFilePath local file path from which to perform the read operation against
     * @throws InterruptedException 
     * @throws IOException 
     */
    private static String ReadFromFile() throws InterruptedException, IOException {
        System.out.println("-----------------------------------------------");
        
        String localFilePath = "src/main/resources/myImage.jpg";
        //String localFilePath = "src/main/resources/test1.png";
        System.out.println("Read with local file: " + localFilePath);
        System.out.println("extention : "+ localFilePath.substring(localFilePath.lastIndexOf(".")+1));
        
        
            File rawImage = new File(localFilePath);
            byte[] localImageBytes = Files.readAllBytes(rawImage.toPath());

            // Cast Computer Vision to its implementation to expose the required methods
            ComputerVisionImpl vision = (ComputerVisionImpl) compVisClient.computerVision();

            // Read in remote image and response header
            Observable<ServiceResponseWithHeaders<Void, ReadInStreamHeaders>> readInStreamWithServiceResponseAsync = vision.readInStreamWithServiceResponseAsync(localImageBytes, null);
            BlockingObservable<ServiceResponseWithHeaders<Void, ReadInStreamHeaders>> blocking = readInStreamWithServiceResponseAsync.toBlocking();
            ServiceResponseWithHeaders<Void, ReadInStreamHeaders> single = blocking.single();
            ReadInStreamHeaders responseHeader = single.headers();
//            ReadInStreamHeaders responseHeader =
//                    vision.readInStreamWithServiceResponseAsync(localImageBytes, null)
//                        .toBlocking()
//                        .single()
//                        .headers();
         // Extract the operationLocation from the response header
            String operationLocation = responseHeader.operationLocation();
            System.out.println("Operation Location:" + operationLocation);

            return operationLocation;
        
    }
    
    /**
     * Polls for Read result and prints results to console
     * @param vision Computer Vision instance
     * @return operationLocation returned in the POST Read response header
     */
    private static void getAndPrintReadResult(String operationLocation) throws InterruptedException {
        System.out.println("Polling for Read results ...");

        // Extract OperationId from Operation Location
        String operationId = extractOperationIdFromOpLocation(operationLocation);

        boolean pollForResult = true;
        ReadOperationResult readResults = null;
        
        ComputerVisionImpl vision = (ComputerVisionImpl) compVisClient.computerVision();

        while (pollForResult) {
            // Poll for result every second
            Thread.sleep(1000);
            readResults = vision.getReadResult(UUID.fromString(operationId));

            // The results will no longer be null when the service has finished processing the request.
            if (readResults != null) {
                // Get request status
                OperationStatusCodes status = readResults.status();

                if (status == OperationStatusCodes.FAILED || status == OperationStatusCodes.SUCCEEDED) {
                    pollForResult = false;
                }
            }
        }
        AnalyzeResults analyzeResult = readResults.analyzeResult();
        try {
			String jsonData = new ObjectMapper().writeValueAsString(analyzeResult);
			System.out.println(jsonData);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     // Print read results, page per page
        for (ReadResult pageResult : analyzeResult.readResults()) {
            System.out.println("");
            System.out.println("Printing Read results for page " + pageResult.page());
            StringBuilder builder = new StringBuilder();

            for (Line line : pageResult.lines()) {
                builder.append(line.text());
                builder.append("\n");
            }

            System.out.println(builder.toString());
        }
    }
    
    /**
     * Extracts the OperationId from a Operation-Location returned by the POST Read operation
     * @param operationLocation
     * @return operationId
     */
    private static String extractOperationIdFromOpLocation(String operationLocation) {
        if (operationLocation != null && !operationLocation.isEmpty()) {
            String[] splits = operationLocation.split("/");

            if (splits != null && splits.length > 0) {
                return splits[splits.length - 1];
            }
        }
        throw new IllegalStateException("Something went wrong: Couldn't extract the operation id from the operation location");
    }
    
    
}