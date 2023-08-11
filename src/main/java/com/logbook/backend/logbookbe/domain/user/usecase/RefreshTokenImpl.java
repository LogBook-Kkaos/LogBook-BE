package com.logbook.backend.logbookbe.domain.user.usecase;

import com.logbook.backend.logbookbe.global.jwt.AuthRole;
import com.logbook.backend.logbookbe.global.jwt.JwtProvider;
import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;

import io.jsonwebtoken.Claims;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenImpl implements RefreshToken {
    private final JwtProvider jwtProvider;

    @Override
    public JwtResponse execute(String refreshToken) {
        jwtProvider.validateToken(refreshToken, true);
        Claims claims = jwtProvider.parseClaims(refreshToken, true);
        String role = claims.get("role", String.class);
        String newAccessToken = jwtProvider.generateToken(claims.getSubject(), AuthRole.valueOf(role), false);
        return new JwtResponse(newAccessToken, null);
    }
}
