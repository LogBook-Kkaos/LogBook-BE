package com.logbook.backend.logbookbe.domain.project.controller.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    private UUID projectId;
    private String message;
}