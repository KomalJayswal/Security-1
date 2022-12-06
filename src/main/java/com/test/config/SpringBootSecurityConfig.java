package com.test.config;

import com.test.utils.CommonUtils;
import com.test.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Slf4j
public class SpringBootSecurityConfig {

    @Value("${FORGEROCK_KEYSTORE}")
    private String forgeRockKeyStore;

    @Value("${FORGEROCK_ISS}")
    private String forgeRockIssuer;
    @Value("${AZURE_KEYSTORE}")
    private String aureAdKeyStore;

    @Value("${AZURE_ISS}")
    private String azureAdIssuer;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.addFilterBefore(new CustomAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION).authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET, "/actuator/**").permitAll().anyExchange().authenticated()).oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));
        return http.build();
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesExtractor());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    public static class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {

        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Collection<?> authorities = (Collection<?>) jwt.getClaims().getOrDefault("roles", Collections.emptyList());
            Collection<GrantedAuthority> authorityCollection = new ArrayList<>();
            if (authorities.isEmpty()) {
                authorities = (Collection<?>) jwt.getClaims().getOrDefault("scope", Collections.emptyList());
                authorityCollection = authorities.stream().map(o -> "SCOPE_" + o.toString()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            } else {
                authorityCollection = authorities.stream().map(o -> "ROLE_" + o.toString()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            }
            log.info("3. Validated Authorization token successfully");

            // anchor call and get all roles and set in context

            return authorityCollection;
        }
    }

    @Bean
    @Primary
    public ReactiveJwtDecoder commonJWTDecoder(ApplicationContext context) {
        return token -> {
            if (Constants.AZURE_TOKEN.equals(CommonUtils.getTokenType(token))) {
                log.info("2. Agent login identified");
                return context.getBean(Constants.AZURE_TOKEN, NimbusReactiveJwtDecoder.class).decode(token);
            } else if (Constants.FORGEROCK_TOKEN.equals(CommonUtils.getTokenType(token))) {
                log.info("2. Customer login identified");
                return context.getBean(Constants.FORGEROCK_TOKEN, NimbusReactiveJwtDecoder.class).decode(token);
            }
            throw new AccessDeniedException("Not a valid token");
        };
    }

    // beans are initialized at the compile time

    @Bean(Constants.FORGEROCK_TOKEN)
    public NimbusReactiveJwtDecoder forgeRockJWTDecoder() {
        log.info("0. NimbusReactiveJwtDecoder for FORGEROCK_TOKEN");
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(forgeRockKeyStore).webClient(webClient()).build();
        OAuth2TokenValidator<Jwt> validator = JwtValidators.createDefaultWithIssuer(forgeRockIssuer);
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }

    @Bean(Constants.AZURE_TOKEN)
    public NimbusReactiveJwtDecoder azureADJwtDecoder() {
        log.info("0. NimbusReactiveJwtDecoder for AZURE_TOKEN");
        NimbusReactiveJwtDecoder jwtDecoder = NimbusReactiveJwtDecoder.withJwkSetUri(aureAdKeyStore).webClient(webClient()).build();
        OAuth2TokenValidator<Jwt> validator = JwtValidators.createDefaultWithIssuer(azureAdIssuer);
        jwtDecoder.setJwtValidator(validator);
        return jwtDecoder;
    }

    private WebClient webClient() {
        return WebClient.builder().defaultHeader(HttpHeaders.USER_AGENT, "INLAND_APP").build();
    }

}
