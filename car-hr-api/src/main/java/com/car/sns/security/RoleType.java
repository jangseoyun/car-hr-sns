package com.car.sns.security;

import lombok.Getter;

public enum RoleType {
    USER("ROLE_USER");

    @Getter
    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}
