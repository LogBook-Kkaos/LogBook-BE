package com.logbook.backend.logbookbe.releaseNote.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ReleaseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer projectId;
    private Integer updateDocumentsId;
    private String releaseTitle;
    private String releaseContent;
    private LocalDateTime creationDate;
    private String version;
    private boolean isImportant;
    private boolean isPublic;
    private String status;

    public void setIsImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

}
