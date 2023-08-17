package com.logbook.backend.logbookbe.releaseContent.controller.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteReleaseContentResponse {

    private UUID releaseContentId;

    private String message;
}
