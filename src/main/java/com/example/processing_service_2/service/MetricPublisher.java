package com.example.processing_service_2.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MetricPublisher {


    private double localEntropy = 0.5;


    @Scheduled(fixedRate = 5000)
    public void publishMetrics() {
// simulate entropy variation for chaos feedback
        localEntropy = 0.8 * localEntropy + 0.2 * Math.random();
        System.out.println("[Service-2 Metrics] entropy=" + String.format("%.3f", localEntropy));
    }
}