package com.logbook.backend.logbookbe.domain.project.model;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="project_id")
    private Integer projectId;

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

}
