package com.test.controller;

import lombok.SneakyThrows;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class HelloController {


    @GetMapping("/hello")
    @SneakyThrows
    public Mono<String> hello(JwtAuthenticationToken authenticationToken, ServerHttpResponse response) {
        return Mono.just("RequestID is " + UUID.randomUUID());
    }
}
