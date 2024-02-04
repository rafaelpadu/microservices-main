package com.microservices.main.reactive.elastic.query.web.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {
    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
        http.authorizeExchange((exchanges) -> exchanges.anyExchange().permitAll())
                .httpBasic(withDefaults());
        http.csrf().disable();
        return http.build();
    }
}
