package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteResponse {
    private Long userId;
    private String message;
}