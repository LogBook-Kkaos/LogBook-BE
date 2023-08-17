package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    private UUID userId;
    private String message;
}