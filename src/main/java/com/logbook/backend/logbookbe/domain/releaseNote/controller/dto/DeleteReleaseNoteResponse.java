package com.logbook.backend.logbookbe.domain.releaseNote.controller.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReleaseNoteResponse {

    private UUID releaseNoteId;

    private String message;
}
