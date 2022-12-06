package com.test.config;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
public class CustomAuthenticationFilter implements WebFilter {

    @Override
    @SneakyThrows
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest httpServletRequest = exchange.getRequest();
        log.info("1. Headers: {}", httpServletRequest.getHeaders());
        return chain.filter(exchange);
    }
}
