package com.logbook.backend.logbookbe.global.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.logbook.backend.logbookbe.global.jwt.AuthRole;
import com.logbook.backend.logbookbe.global.jwt.JwtProvider;
import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuthDetails oAuthDetails = (OAuthDetails) authentication.getPrincipal();
        String email = oAuthDetails.getEmail();
        String accessToken = jwtProvider.generateToken(email, AuthRole.ROLE_ADMIN, false);
        String refreshToken = jwtProvider.generateToken(email, AuthRole.ROLE_ADMIN, true);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);

        Map<String, Object> result = new HashMap<>();
        result.put("result", new JwtResponse(accessToken, refreshToken));

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
