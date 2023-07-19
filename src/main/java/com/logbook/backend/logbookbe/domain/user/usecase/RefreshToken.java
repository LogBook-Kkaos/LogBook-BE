package com.logbook.backend.logbookbe.domain.user.usecase;

import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;

public interface RefreshToken {
    JwtResponse execute(String refreshToken);
}
