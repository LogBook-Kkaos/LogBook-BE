package com.logbook.backend.logbookbe.releaseContent.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DocumentRequest {

    private UUID documentId;

    private String documentTitle;
}
