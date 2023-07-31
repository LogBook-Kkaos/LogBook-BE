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
    private Integer projectId;

    @Column(nullable = false)
    private String projectName;

    @Lob
    private String projectDescription;

    @Column(nullable = false)
    private boolean isPublic;

    private Integer memberCount;

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
