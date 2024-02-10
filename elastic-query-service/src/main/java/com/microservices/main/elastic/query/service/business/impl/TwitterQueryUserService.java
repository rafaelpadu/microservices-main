package com.microservices.main.elastic.query.service.business.impl;

import com.microservices.main.elastic.query.service.business.QueryUserService;
import com.microservices.main.elastic.query.service.dataaccess.entity.UserPermission;
import com.microservices.main.elastic.query.service.dataaccess.repository.UserPermissionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TwitterQueryUserService implements QueryUserService {
    private static final Logger log = LoggerFactory.getLogger(TwitterQueryUserService.class);
    private final UserPermissionRepository userPermissionRepository;

    public TwitterQueryUserService(UserPermissionRepository userPermissionRepository) {
        this.userPermissionRepository = userPermissionRepository;
    }

    @Override
    public Optional<List<UserPermission>> findAllPermissionsByUsername(String username) {
        log.info("Finding permissions by username {}", username);
        Optional<List<UserPermission>> lista =  userPermissionRepository.buscaAlgoEmparticular();
        return lista;
    }
}
