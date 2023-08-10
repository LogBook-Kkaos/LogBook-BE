package com.logbook.backend.logbookbe.domain.project.model;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "project_id", columnDefinition = "VARCHAR(36)")
    private UUID projectId = UUID.randomUUID();

    @Column(name="project_name", nullable = false)
    private String projectName;

    @Lob
    @Column(name="project_description")
    private String projectDescription;

    @Column(name="is_public", nullable = false)
    private boolean isPublic;

    @Column(name="member_count")
    private Integer memberCount;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues = new ArrayList<>();


    public Project(UUID ProjectId, String projectName, String projectDescription, boolean isPublic) {
        this.setProjectId(projectId);
        this.setProjectName(projectName);
        this.setProjectDescription(projectDescription);
        this.setPublic(isPublic);
    }
}
