package com.logbook.backend.logbookbe.domain.releaseNote.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ReleaseNote {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "release_note_id", columnDefinition = "VARCHAR(36)")
    private UUID releaseNoteId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Member creator;

    @Column(name = "release_title", nullable = false)
    private String releaseTitle;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "is_important")
    private boolean isImportant;

    @Column(name = "is_public")
    private boolean isPublic;

//    public void setIsImportant(boolean isImportant) {
//        this.isImportant = isImportant;
//    }
//
//    public void setIsPublic(boolean isPublic) {
//        this.isPublic = isPublic;
//    }

}
