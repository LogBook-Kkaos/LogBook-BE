package com.logbook.backend.logbookbe.domain.member.controller.dto;

import com.logbook.backend.logbookbe.domain.member.type.PermissionLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequest {
    private String email;
    private PermissionLevel permissionLevel;
}
