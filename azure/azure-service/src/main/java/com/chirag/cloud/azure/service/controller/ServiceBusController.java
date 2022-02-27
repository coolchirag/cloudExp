package com.chirag.cloud.azure.service.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.azure.core.util.IterableStream;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverAsyncClient;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.chirag.cloud.azure.service.servicebus.ServiceBusService;
import com.chirag.cloud.azure.service.servicebus.ThreadForServiceBusMsgRenew;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("serviceBus")
public class ServiceBusController {

	@Autowired
	private ServiceBusService sbs;

	@RequestMapping("/")
	String hello() throws Exception {
		ServiceBusReceiverAsyncClient serviceBusReceiverClientAsync = sbs
				.getServiceBusReceiverClientAsync("temp_test_queue");
		// ServiceBusReceiverClient serviceBusReceiverClient =
		// sbs.getServiceBusReceiverClient("temp_test_queue");
		Flux<ServiceBusReceivedMessage> receiveQueueMessageAsync = sbs
				.receiveQueueMessageAsync(serviceBusReceiverClientAsync);
		receiveQueueMessageAsync.subscribe(msg -> {
			String msgBody = msg.getBody().toString();
			System.out.println("Renewing.... : " + msgBody);
			serviceBusReceiverClientAsync.renewMessageLock(msg, Duration.ofMinutes(8));
			System.out.println(msgBody);
			try {
				for (int i = 1; i <= 8; i++) {
					System.out.println("Start Wait time : " + i + " minutes is started.");
					Thread.sleep(60 * 1000);
					System.out.println("End Wait time : " + i + " minutes is completed.");
				}

				// Thread.sleep(3*60*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sbs.completeMessageAsync(serviceBusReceiverClientAsync, msg);
			System.out.println("Completed.... : " + msgBody);
		});
		return "Hello World!";

	}

	@RequestMapping("/test2")
	String hello2() throws Exception {
		ServiceBusReceiverClient serviceBusReceiverClient = sbs
				.getServiceBusReceiverClientWithManagedIdentity("temp_test_queue");
		IterableStream<ServiceBusReceivedMessage> receiveQueueMessage = sbs
				.receiveQueueMessage(serviceBusReceiverClient);
		receiveQueueMessage.forEach(msg -> {
			String msgBody = msg.getBody().toString();
			ThreadForServiceBusMsgRenew renewTask = new ThreadForServiceBusMsgRenew(serviceBusReceiverClient, msg);
			// Thread t1 = new Thread(renewTask);
			// t1.start();

			serviceBusReceiverClient.renewMessageLock(msg, Duration.ofMinutes(12), System.out::println);
			System.out.println(msgBody);
			/*
			 * try { for (int i = 1; i <= 8; i++) { System.out.println("Start Wait time : "
			 * + i + " minutes is started."); Thread.sleep(60 * 1000);
			 * System.out.println("Wait time : " + i + " minutes is completed."); } } catch
			 * (InterruptedException e) { e.printStackTrace(); }
			 */

			sbs.completeMessage(serviceBusReceiverClient, msg);
			System.out.println("Completed.... : " + msgBody);
		});
		return "Hello World!";

	}

}
