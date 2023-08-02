package com.logbook.backend.logbookbe.domain.issue.model;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Integer issueId;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "assignee_id")
    private Integer assignee;
    @Column(name = "start_date")
    private Timestamp startDate;
    @Column(name = "end_date")
    private Timestamp endDate;
    @Column(name = "issue_title", nullable = false)
    private String issueTitle;
    @Column(name = "issue_description")
    private String issueDescription;
    @Column(name = "status")
    private String status;
}
