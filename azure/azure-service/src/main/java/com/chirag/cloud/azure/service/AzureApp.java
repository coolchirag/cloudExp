package com.chirag.cloud.azure.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class AzureApp {
    public static void main(String[] args) {
        SpringApplication.run(AzureApp.class, args);
		/*
		 * Mono<String> data = Mono.just("hello"); data.block();
		 */
        
    }
} 