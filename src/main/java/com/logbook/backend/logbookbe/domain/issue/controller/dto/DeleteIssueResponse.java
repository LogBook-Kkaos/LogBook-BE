package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteIssueResponse {
    private UUID issueId;
    private String message;
}