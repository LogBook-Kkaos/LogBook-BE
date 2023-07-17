package com.logbook.backend.logbookbe.domain.user.usecase;

import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;

public interface UserSignup {
    JwtResponse execute(String userName, String email, String department, String password);
}
