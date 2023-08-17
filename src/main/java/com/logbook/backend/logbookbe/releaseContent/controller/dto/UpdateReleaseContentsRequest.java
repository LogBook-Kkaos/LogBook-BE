package com.logbook.backend.logbookbe.releaseContent.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UpdateReleaseContentsRequest {

    private List<UUID> releaseContentIds;
    private List<CreateReleaseContentRequest> updatedReleaseContentDTOs;
}
