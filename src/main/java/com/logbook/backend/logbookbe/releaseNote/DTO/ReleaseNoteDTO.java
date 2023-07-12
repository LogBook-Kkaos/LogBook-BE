package com.logbook.backend.logbookbe.releaseNote.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReleaseNoteDTO {
    private Integer projectId;
    private Integer updateDocumentsId;
    private String releaseTitle;
    private String releaseContent;
    private LocalDateTime creationDate;
    private String version;
    private boolean isImportant;
    private boolean isPublic;
    private String status;

    public ReleaseNoteDTO() {
    }

    public ReleaseNoteDTO(Integer projectId, Integer updateDocumentsId, String releaseTitle, String releaseContent, LocalDateTime creationDate, String version, boolean isImportant, boolean isPublic, String status) {
        this.projectId = projectId;
        this.updateDocumentsId = updateDocumentsId;
        this.releaseTitle = releaseTitle;
        this.releaseContent = releaseContent;
        this.creationDate = creationDate;
        this.version = version;
        this.isImportant = isImportant;
        this.isPublic = isPublic;
        this.status = status;
    }


}


