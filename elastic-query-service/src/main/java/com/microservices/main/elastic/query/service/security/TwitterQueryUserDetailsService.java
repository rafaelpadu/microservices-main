package com.microservices.main.elastic.query.service.security;

import com.microservices.main.elastic.query.service.business.QueryUserService;
import com.microservices.main.elastic.query.service.transformer.UserPermissionsToUserDetailTransformer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TwitterQueryUserDetailsService implements UserDetailsService {
    private final QueryUserService queryUserService;
    private final UserPermissionsToUserDetailTransformer permissionsToUserDetailTransformer;

    public TwitterQueryUserDetailsService(QueryUserService queryUserService, UserPermissionsToUserDetailTransformer permissionsToUserDetailTransformer) {
        this.queryUserService = queryUserService;
        this.permissionsToUserDetailTransformer = permissionsToUserDetailTransformer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return queryUserService
                .findAllPermissionsByUsername(username)
                .map(permissionsToUserDetailTransformer::getUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with name " + username));
    }
}
