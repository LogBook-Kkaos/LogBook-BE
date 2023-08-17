package com.logbook.backend.logbookbe.domain.member.controller.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponse {
    private UUID memberId;
    private String message;
}