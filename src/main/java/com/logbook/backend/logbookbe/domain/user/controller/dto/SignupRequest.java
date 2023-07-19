package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String userName;
    private String email;
    private String department;
    private String password;
}
