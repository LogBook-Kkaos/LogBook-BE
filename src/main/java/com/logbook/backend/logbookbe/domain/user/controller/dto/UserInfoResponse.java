package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoResponse {
    private String userName;
    private String email;
    private String department;
}