package com.example.gateway_service.service;

import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AdaptiveLoadModulator {

    private final WebClient webClient;
    private final ChaosStabilityEngine engine = new ChaosStabilityEngine();
    private final AtomicReference<Double> weightP1 = new AtomicReference<>(0.7);

    public AdaptiveLoadModulator() {
        HttpClient httpClient = HttpClient.create()
                // ✅ Fix: Ensures Docker DNS names like "processing-service-1" resolve correctly
                .resolver(DefaultAddressResolverGroup.INSTANCE);

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient)) // ✅ Correct class
                .codecs(cfg -> cfg.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    public Mono<String> forwardRequest(String mode) {
        engine.recordSample(Math.random());
        double newWeight = engine.computeWeight(weightP1.get());
        weightP1.set(newWeight);

        boolean toP1 = Math.random() < newWeight;
        String target = toP1
                ? "http://processing-service-1:8081/process"
                : "http://processing-service-2:8082/process";

        System.out.println("⚙️ Routing to: " + target);

        return webClient.get()
                .uri(target + "?mode=" + mode)
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5))
                .doOnError(e -> {
                System.out.println("❌ WebClient failed while calling " + target + ": " + e.getClass().getName() + " -> " + e.getMessage());
        })
                .onErrorResume(e -> Mono.just("Service unavailable"));
    }
}
