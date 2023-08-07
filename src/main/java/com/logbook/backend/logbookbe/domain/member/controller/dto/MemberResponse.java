package com.logbook.backend.logbookbe.domain.member.controller.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private Integer memberId;
    private String message;
}