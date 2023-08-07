package com.logbook.backend.logbookbe.domain.member.controller.dto;

import com.logbook.backend.logbookbe.domain.member.type.PermissionLevel;
import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditMemberResponse {
    private UUID memberId;
    private PermissionLevel permissionLevel;
}
