package com.chirag.cloud.azure.service.servicebus;

import java.time.Duration;

import org.springframework.stereotype.Service;

import com.azure.core.util.IterableStream;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder.ServiceBusReceiverClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.azure.messaging.servicebus.ServiceBusSenderClient;

import reactor.core.publisher.Flux;

@Service
public class ServiceBusService {

	private String connectionUrl = "Endpoint=sb://dev-coding-platform-service-bus.servicebus.usgovcloudapi.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=hyFU6K73qveK2grMr25YkzQG/b4zsk6Jvtrwb9byqfI=";
			//"Endpoint=sb://<hostname>/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=<sharedAccessKey>"; 
	//GetConnectionString from "Shared access policies" -> select policy		
	
	private String fullyQualifiedNameSpace ="dev-coding-platform-service-bus.servicebus.usgovcloudapi.net"; 
			//"<HostName>";
	
	private final String managedIdentityClientId = "4588566e-6c59-47be-a2a4-7b4b57136238";
			//"<MagedIdentityClientId>";

	public ServiceBusSenderClient getSreviceBusSenderClient(final String queueName) throws Exception {
		return new ServiceBusClientBuilder()
				.connectionString(connectionUrl)
				/*
				 * .connectionString("Endpoint=" + connectionUrl +
				 * ";SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=" +
				 * sharedKey)
				 */
				.sender().queueName(queueName).buildClient();
	}

	public ServiceBusReceiverClient getServiceBusReceiverClient(final String queueName) throws Exception {
		return new ServiceBusClientBuilder()
				.connectionString(connectionUrl)
				/*
				 * .connectionString("Endpoint=" + connectionUrl +
				 * ";SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=" +
				 * sharedKey)
				 */
				.receiver().queueName(queueName).buildClient();
	}
	
	public ServiceBusSenderClient getSreviceBusSenderClientWithManagedIdentity(final String queueName) throws Exception {
		ManagedIdentityCredential credential = new ManagedIdentityCredentialBuilder().clientId(managedIdentityClientId).build();
		return new ServiceBusClientBuilder().credential(fullyQualifiedNameSpace, credential)
				.sender().queueName(queueName).buildClient();
	}

	public ServiceBusReceiverClient getServiceBusReceiverClientWithManagedIdentity(final String queueName) throws Exception {
		ManagedIdentityCredential credential = new ManagedIdentityCredentialBuilder().clientId(managedIdentityClientId).build();
		return new ServiceBusClientBuilder()
				.credential(fullyQualifiedNameSpace,credential)
				.receiver().queueName(queueName).buildClient();
	}

	public ServiceBusReceiverAsyncClient getServiceBusReceiverClientAsync(final String queueName) throws Exception {
		ServiceBusReceiverClientBuilder builder = new ServiceBusClientBuilder()
				.connectionString(connectionUrl)
				/*
				 * .connectionString("Endpoint=" + connectionUrl +
				 * ";SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=" +
				 * sharedKey)
				 */
				.receiver().queueName(queueName);
		// .maxAutoLockRenewDuration(Duration.ofMinutes(7));
		return builder.buildAsyncClient();
	}

	public void sendQueueMessage(ServiceBusSenderClient senderClient, String message) throws Exception {
		senderClient.sendMessage(new ServiceBusMessage(message));

	}

	public IterableStream<ServiceBusReceivedMessage> receiveQueueMessage(ServiceBusReceiverClient receiverClient) {
		return receiveQueueMessage(receiverClient, 1);
	}

	public IterableStream<ServiceBusReceivedMessage> receiveQueueMessage(ServiceBusReceiverClient receiverClient,
			int messageCount) {
		return receiverClient.receiveMessages(messageCount, Duration.ofSeconds(5));
	}

	public Flux<ServiceBusReceivedMessage> receiveQueueMessageAsync(ServiceBusReceiverAsyncClient receiverClient) {
		return receiverClient.receiveMessages();
	}

	public Boolean completeMessage(ServiceBusReceiverClient serviceBusReceiverClient,
			ServiceBusReceivedMessage message) {
		serviceBusReceiverClient.complete(message);
		return true;
	}

	public Boolean completeMessageAsync(ServiceBusReceiverAsyncClient receiverClient,
			ServiceBusReceivedMessage message) {
		receiverClient.complete(message);
		return true;
	}
}
