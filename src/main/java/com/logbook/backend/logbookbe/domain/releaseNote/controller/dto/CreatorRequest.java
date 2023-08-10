package com.logbook.backend.logbookbe.domain.releaseNote.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatorRequest {

    private UUID creatorId;

    private String userName;

}
