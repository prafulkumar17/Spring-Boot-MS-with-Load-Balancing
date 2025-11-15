package com.example.processing_service_2.controller;

import com.example.processing_service_2.service.OverflowHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TaskController {


    private final OverflowHandler handler;


    public TaskController(OverflowHandler handler) {
        this.handler = handler;
    }


    @GetMapping("/process")
    public ResponseEntity<String> process(@RequestParam(required = false, defaultValue = "fast") String mode) throws InterruptedException {
        long duration = handler.handleTask(mode);
        return ResponseEntity.ok("Processed by Service-2 (Overflow) | Mode=" + mode + " | Duration=" + duration + "ms");
    }


    @GetMapping("/metrics-simple")
    public ResponseEntity<String> metrics() {
        return ResponseEntity.ok(handler.getStabilityState());
    }
}