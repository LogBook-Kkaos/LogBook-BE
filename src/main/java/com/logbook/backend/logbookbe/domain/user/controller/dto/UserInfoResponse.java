package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponse {
    private String userName;
    private String email;
    private String department;
}