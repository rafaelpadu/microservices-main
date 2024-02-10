package com.microservices.main.elastic.query.service.security;

import lombok.Getter;

@Getter
public enum PermissionType {
    READ("READ"), WRITE("WRITE"), ADMIN("ADMIN");

    private String type;

    PermissionType(String type) {
        this.type = type;
    }

}
