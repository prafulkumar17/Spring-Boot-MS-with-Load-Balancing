package com.example.gateway_service.service;

public class ChaosStabilityEngine {


    private double x = 0.5;
    private static final double R = 3.6;


    public void recordSample(double metric) {
        x = 0.9 * x + 0.1 * Math.max(0.0, Math.min(1.0, metric));
        x = R * x * (1 - x);
    }


    public double computeWeight(double previous) {
        double candidate = 0.5 + (x - 0.5);
        double smoothed = 0.7 * previous + 0.3 * candidate;
        if (smoothed < 0.1) smoothed = 0.1;
        if (smoothed > 0.9) smoothed = 0.9;
        return smoothed;
    }
}