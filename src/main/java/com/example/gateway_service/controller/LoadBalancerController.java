package com.example.gateway_service.controller;

import com.example.gateway_service.service.AdaptiveLoadModulator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class LoadBalancerController {

    private final AdaptiveLoadModulator modulator;

    public LoadBalancerController(AdaptiveLoadModulator modulator) {
        this.modulator = modulator;
    }

    @GetMapping("/invoke")
    public Mono<ResponseEntity<String>> invoke(@RequestParam(defaultValue = "fast") String mode) {
        System.out.println("🎯 Received /invoke request with mode = " + mode);
        return modulator.forwardRequest(mode)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.status(503).body("Service unavailable"));
    }
}