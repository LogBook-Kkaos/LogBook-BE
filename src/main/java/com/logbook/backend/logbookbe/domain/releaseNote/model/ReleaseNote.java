package com.logbook.backend.logbookbe.domain.releaseNote.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "releaseNote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReleaseContent> releaseContents = new ArrayList<>();

//    public void setIsImportant(boolean isImportant) {
//        this.isImportant = isImportant;
//    }
//
//    public void setIsPublic(boolean isPublic) {
//        this.isPublic = isPublic;
//    }

}
