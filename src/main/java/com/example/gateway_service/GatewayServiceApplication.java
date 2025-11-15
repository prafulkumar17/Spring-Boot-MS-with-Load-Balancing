package com.example.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        try {
            System.out.println("⏳ Waiting for Eureka to populate registry...");
            Thread.sleep(10000); // wait 10 seconds before starting
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // ✅ restore interrupt status
            System.out.println("⚠️ Startup delay interrupted.");
        }

        SpringApplication.run(GatewayServiceApplication.class, args);
    }
}
