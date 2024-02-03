package com.microservices.main.elastic.query.web.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class WebSecurityConfig{
//    private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);
//
//    private static final String GROUPS_CLAIM = "groups";
//
//    private static final String ROLE_PREFIX = "ROLE_";
//
//    private final ClientRegistrationRepository clientRegistrationRepository;
//
//    @Value("${security.logout-success-url}")
//    private String logoutSuccessUrl;
//
//    public WebSecurityConfig(ClientRegistrationRepository registrationRepository) {
//        this.clientRegistrationRepository = registrationRepository;
//    }
//
//    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
//        OidcClientInitiatedLogoutSuccessHandler successHandler =
//                new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
//        successHandler.setPostLogoutRedirectUri(logoutSuccessUrl);
//        return successHandler;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers(new AntPathRequestMatcher("/"))
//                        .permitAll()
//                        .anyRequest().authenticated())
//                .logout(conf -> conf.logoutSuccessHandler(oidcLogoutSuccessHandler()))
//                .oauth2Client(Customizer.withDefaults())
//                .oauth2Login(conf -> conf.userInfoEndpoint(Customizer.withDefaults()));
//        return http.build();
//    }
//
//    @Bean
//    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
//        return (authorities) -> {
//            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//            authorities.forEach(
//                    authority -> {
//                        if (authority instanceof OidcUserAuthority) {
//                            OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//                            OidcIdToken oidcIdToken = oidcUserAuthority.getIdToken();
//                            LOG.info("Username from id token: {}", oidcIdToken.getPreferredUsername());
//                            OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//
//                            List<SimpleGrantedAuthority> groupAuthorities =
//                                    userInfo.getClaimAsStringList(GROUPS_CLAIM).stream()
//                                            .map(group ->
//                                                    new SimpleGrantedAuthority(ROLE_PREFIX + group.toUpperCase()))
//                                            .collect(Collectors.toList());
//                            mappedAuthorities.addAll(groupAuthorities);
//                        }
//                    });
//            return mappedAuthorities;
//        };
//    }
}
