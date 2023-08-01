package com.logbook.backend.logbookbe.domain.member.controller.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditMemberResponse {
    private Integer memberId;
    private String permissionLevel;
    private String role;
}
