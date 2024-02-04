package br.com.microservices.main.reactive.elastic.query.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
//    @Bean
//    public SecurityWebFilterChain webFluxSecurityConfig(ServerHttpSecurity httpSecurity) {
//        httpSecurity.authorizeExchange()
//                .anyExchange()
//                .permitAll();
//        httpSecurity.csrf().disable();
//        return httpSecurity.build();
//    }

    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
        http.authorizeExchange((exchanges) -> exchanges.anyExchange().permitAll())
                .httpBasic(withDefaults());
        http.csrf().disable();
        return http.build();
    }

}
