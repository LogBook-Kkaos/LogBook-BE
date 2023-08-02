package com.logbook.backend.logbookbe.domain.project.model;

import lombok.*;

import javax.persistence.*;

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

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
