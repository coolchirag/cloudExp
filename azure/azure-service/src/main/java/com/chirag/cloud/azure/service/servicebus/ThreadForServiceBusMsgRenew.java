package com.chirag.cloud.azure.service.servicebus;

import java.time.OffsetDateTime;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;

public class ThreadForServiceBusMsgRenew implements Runnable {

	private ServiceBusReceiverClient serviceBusReceiverClient;
	private ServiceBusReceivedMessage msg;

	public ThreadForServiceBusMsgRenew(ServiceBusReceiverClient serviceBusReceiverClient,
			ServiceBusReceivedMessage msg) {
		super();
		this.serviceBusReceiverClient = serviceBusReceiverClient;
		this.msg = msg;
	}

	@Override
	public void run() {
		OffsetDateTime expiresAt = msg.getLockedUntil();
		while (true) {
			
			Long expLong = expiresAt.toInstant().toEpochMilli();
			System.out.println(expiresAt + " : " + expLong);
			OffsetDateTime now = OffsetDateTime.now();
			Long nowLong = now.toInstant().toEpochMilli();
			System.out.println(now + " : " + nowLong);
			long diff = expLong - nowLong - 30000;
			if (diff <= 0) {
				System.out.println("Msg : " + msg.getBody() + " is renewing...");
				expiresAt = serviceBusReceiverClient.renewMessageLock(msg);
			} else {
				try {
					Thread.sleep(diff);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
