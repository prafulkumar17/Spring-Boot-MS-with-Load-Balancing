package com.example.processing_service_2.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class OverflowHandler {


    private final AtomicInteger activeTasks = new AtomicInteger(0);
    private double stabilizationFactor = 1.0; // dynamic stability control


    public long handleTask(String mode) throws InterruptedException {
        activeTasks.incrementAndGet();
        long start = System.currentTimeMillis();


        try {
            int baseDelay = "slow".equals(mode) ? 250 : 70;
            int noise = ThreadLocalRandom.current().nextInt(100);
            int delay = (int) (stabilizationFactor * (baseDelay + noise));
            Thread.sleep(delay);
        } finally {
            activeTasks.decrementAndGet();
        }


// adapt stabilization factor to current load (anti-synchronization)
        adjustStabilization();
        return System.currentTimeMillis() - start;
    }


    private void adjustStabilization() {
        int load = activeTasks.get();
        if (load > 5) stabilizationFactor = 0.8; // speed up under high load
        else if (load < 2) stabilizationFactor = 1.1; // slow down slightly under low load
        else stabilizationFactor = 1.0;
    }


    public String getStabilityState() {
        return "ActiveTasks=" + activeTasks.get() + ", StabilizationFactor=" + stabilizationFactor;
    }
}