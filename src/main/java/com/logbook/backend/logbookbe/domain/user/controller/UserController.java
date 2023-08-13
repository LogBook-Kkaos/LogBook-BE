package com.logbook.backend.logbookbe.domain.user.controller;

import com.logbook.backend.logbookbe.domain.member.service.MemberService;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.controller.dto.*;
import com.logbook.backend.logbookbe.domain.user.exception.UserNotFoundException;
import com.logbook.backend.logbookbe.domain.user.model.User;
import com.logbook.backend.logbookbe.domain.auth.usecase.AddTokenToBlackList;
import com.logbook.backend.logbookbe.global.error.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.logbook.backend.logbookbe.domain.user.usecase.RefreshToken;
import com.logbook.backend.logbookbe.domain.user.usecase.UserSignup;
import com.logbook.backend.logbookbe.global.jwt.dto.JwtResponse;
import com.logbook.backend.logbookbe.domain.user.service.UserService;
import com.logbook.backend.logbookbe.domain.user.usecase.UserLogin;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.UUID;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MemberService memberService;

    private final UserLogin userLogin;
    private final UserSignup userSignup;
    private final RefreshToken doRefreshToken;
    private final AddTokenToBlackList addTokenToBlackList;

    @Operation(summary = "사용자 로그인", description = "사용자 계정으로 로그인합니다. RefreshToken은 Cookie에 자동으로 추가됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletResponse res, @Validated @RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = userLogin.execute(loginRequest.getEmail(), loginRequest.getPassword());
        User user = userService.findUserByEmail(loginRequest.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("userName", user.getUserName());
        response.put("email", user.getEmail());
        response.put("department", user.getDepartment());
        response.put("jwt", jwt);

        return ResponseEntity.ok(response);
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
        return jwt;
    }

    @Operation(summary = "사용자 로그아웃", description = "쿠키에 저장된 RefreshToken을 삭제하고 로그아웃 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/logout")
    public boolean logout(@Parameter(hidden = true) @CookieValue("refreshToken") String oldToken, HttpServletResponse res) {
        addTokenToBlackList.execute(oldToken);
        return true;
    }

    @PutMapping("/{user_id}")
    @Operation(summary = "사용자 수정", description = "기존 사용자 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public User updateUser(@PathVariable("user_id") UUID userId, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(userId);
        if (existingUser == null) {
            throw new UserNotFoundException();
        }

        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setDepartment(updatedUser.getDepartment());
        existingUser.setPassword(updatedUser.getPassword());

        return userService.updateUser(existingUser);
    }

    @Operation(summary = "전체 사용자 조회", description = "모든 사용자 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<SearchResponse> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<SearchResponse> searchResults = new ArrayList<>();

        for (User user : users) {
            SearchResponse searchResult = new SearchResponse();
            searchResult.setEmail(user.getEmail());
            searchResult.setUserName(user.getUserName());

            searchResults.add(searchResult);
        }
        return searchResults;
    }

    @Operation(summary = "사용자 상세 조회", description = "특정 사용자의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{user_id}")
    public UserInfoResponse getUserById(@PathVariable("user_id") UUID userId) {
        User user = userService.getUserById(userId);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUserName(user.getUserName());
        userInfoResponse.setEmail(user.getEmail());
        userInfoResponse.setDepartment(user.getDepartment());

        return userInfoResponse;
    }

    @Operation(summary = "사용자 검색", description = "사용자를 키워드로 검색합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = SearchResponse.class)))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
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

    @DeleteMapping("/{user_id}")
    @Operation(summary = "사용자 삭제", description = "특정 사용자를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = DeleteResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public DeleteResponse deleteUser(@PathVariable("user_id") UUID userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new UserNotFoundException();
        }
        userService.deleteUser(userId);
        return new DeleteResponse(userId, "User deleted successfully");
    }

    @GetMapping("/myproject")
    @Operation(summary = "사용자의 프로젝트 목록 조회", description = "특정 사용자의 프로젝트 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjectInfoResponse.class)))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ProjectInfoResponse>> getUserProjects(@RequestParam("email") String email) {
        User user = userService.findUserByEmail(email);
        List<Project> projects = memberService.findMyProject(user.getUserId());
        List<ProjectInfoResponse> projectInfos = new ArrayList<>();

        for (Project project : projects) {
            ProjectInfoResponse projectInfo = new ProjectInfoResponse();
            projectInfo.setProjectId(project.getProjectId());
            projectInfo.setProjectName(project.getProjectName());
            projectInfos.add(projectInfo);
        }

        return ResponseEntity.ok(projectInfos);
    }

}

