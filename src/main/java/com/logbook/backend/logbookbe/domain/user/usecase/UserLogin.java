package com.logbook.backend.logbookbe.domain.user.usecase;

import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;

public interface UserLogin {
    JwtResponse execute(String email, String password);
}
