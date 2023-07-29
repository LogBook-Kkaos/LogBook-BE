package com.logbook.backend.logbookbe.domain.project.controller.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    private Integer project_id;
    private String message;
}