package com.logbook.backend.logbookbe.user.controller;

import com.logbook.backend.logbookbe.user.model.User;
import com.logbook.backend.logbookbe.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // 로거 인스턴스 생성
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // 들어온 User 객체 정보를 로그에 기록
        logger.info("Received User: {}", user);

        try {
            User savedUser = userService.createUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error while creating user: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
