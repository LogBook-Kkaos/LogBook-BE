package com.logbook.backend.logbookbe.domain.user.controller;

import com.logbook.backend.logbookbe.domain.user.controller.dto.SearchResponse;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.logbook.backend.logbookbe.domain.user.controller.dto.LoginRequest;
import com.logbook.backend.logbookbe.domain.user.controller.dto.SignupRequest;
import com.logbook.backend.logbookbe.domain.user.usecase.RefreshToken;
import com.logbook.backend.logbookbe.domain.user.usecase.UserSignup;
import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;
import com.logbook.backend.logbookbe.domain.user.service.UserService;
import com.logbook.backend.logbookbe.domain.user.usecase.UserLogin;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    // 로거 인스턴스 생성
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserLogin userLogin;
    private final UserSignup userSignup;
    private final RefreshToken doRefreshToken;

    @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인합니다. RefreshToken은 Cookie에 자동으로 추가됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public JwtResponse login(HttpServletResponse res, @Validated @RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = userLogin.execute(loginRequest.getEmail(), loginRequest.getPassword());
        Cookie cookie = new Cookie("refreshToken", jwt.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return jwt;
    }

    @Operation(summary = "사용자 토큰갱신", description = "AccessToken을 갱신합니다. 이 때 Cookie에 있는 RefreshToken을 자동으로 가져와 사용합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refresh")
    public JwtResponse refresh(@Parameter(hidden = true) @CookieValue("refreshToken") String refreshToken) {
        return doRefreshToken.execute(refreshToken);
    }

    @Operation(summary = "사용자 회원가입", description = "사용자 계정으로 회원가입합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public JwtResponse signup(HttpServletResponse res, @Validated @RequestBody SignupRequest signupRequest) {
        JwtResponse jwt = userSignup.execute(signupRequest.getUserName(), signupRequest.getEmail(), signupRequest.getDepartment(), signupRequest.getPassword());
        Cookie cookie = new Cookie("refreshToken", jwt.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        res.addCookie(cookie);
        return jwt;
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResponse>> searchSurveys(@RequestParam("keyword") String keyword) {
        System.out.println("searchSurveys() called");
        System.out.println("keyword is: " + keyword);

        List<User> users = userService.searchUsers(keyword);
        List<SearchResponse> searchResults = new ArrayList<>();

        for (User user : users) {
            SearchResponse searchResult = new SearchResponse();
            searchResult.setEmail(user.getEmail());
            searchResult.setUserName(user.getUserName());

            searchResults.add(searchResult);
        }

        return ResponseEntity.ok(searchResults);
    }
}
