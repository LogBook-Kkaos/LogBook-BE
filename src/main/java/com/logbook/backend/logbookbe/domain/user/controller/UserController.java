package com.logbook.backend.logbookbe.domain.user.controller;

import com.logbook.backend.logbookbe.domain.user.controller.dto.LoginRequest;
import com.logbook.backend.logbookbe.domain.user.controller.dto.SignupRequest;
import com.logbook.backend.logbookbe.domain.user.usecase.RefreshToken;
import com.logbook.backend.logbookbe.domain.user.usecase.UserSignup;
import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.user.service.UserService;
import com.logbook.backend.logbookbe.domain.user.usecase.UserLogin;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    // 로거 인스턴스 생성
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserLogin userLogin;
    private final UserSignup userSignup;
    private final RefreshToken doRefreshToken;

    @PostMapping("/login")
    public JwtResponse login(HttpServletResponse res, @Validated @RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = userLogin.execute(loginRequest.getEmail(), loginRequest.getPassword());
        Cookie cookie = new Cookie("refreshToken", jwt.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return jwt;
    }

    @PostMapping("/refresh")
    public JwtResponse refresh(@CookieValue("refreshToken") String refreshToken) {
        return doRefreshToken.execute(refreshToken);
    }

    @PostMapping("/signup")
    public JwtResponse signup(HttpServletResponse res, @Validated @RequestBody SignupRequest signupRequest) {
        JwtResponse jwt = userSignup.execute(signupRequest.getUserName(), signupRequest.getEmail(), signupRequest.getDepartment(), signupRequest.getPassword());
        Cookie cookie = new Cookie("refreshToken", jwt.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return jwt;
    }
}
