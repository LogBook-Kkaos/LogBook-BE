package com.logbook.backend.logbookbe.global.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthRole {
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;
}
