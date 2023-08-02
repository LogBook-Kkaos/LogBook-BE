package com.logbook.backend.logbookbe.domain.issue.controller.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueDeleteResponse {
    private Integer issueId;
    private String message;
}