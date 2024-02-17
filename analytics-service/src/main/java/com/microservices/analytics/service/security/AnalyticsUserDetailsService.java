package com.microservices.analytics.service.security;

import com.microservices.analytics.service.security.AnalyticsUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return AnalyticsUser.builder()
                .username(username)
                .build();
    }
}
