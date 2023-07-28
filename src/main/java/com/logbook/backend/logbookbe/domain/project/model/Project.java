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
    private Integer project_id;

    @Column(nullable = false)
    private String project_name;

    @Lob
    private String project_description;

    @Column(nullable = false)
    private short is_public;

    @Column(nullable = false)
    private String status;

    private Integer member_count;
}
