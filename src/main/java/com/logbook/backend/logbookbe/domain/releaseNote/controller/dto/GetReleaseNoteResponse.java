package com.logbook.backend.logbookbe.domain.releaseNote.controller.dto;

import com.logbook.backend.logbookbe.domain.member.model.Member;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;


@Getter
@Setter
public class GetReleaseNoteResponse {

    private UUID releaseNoteId;

    private CreatorRequest creator;

    private String releaseTitle;

    private Timestamp creationDate;

    private String version;

    private boolean isImportant;

    private boolean isPublic;

//    public void setIsImportant(boolean isImportant) {
//        this.isImportant = isImportant;
//    }
//
//    public void setIsPublic(boolean isPublic) {
//        this.isPublic = isPublic;
//    }

}
