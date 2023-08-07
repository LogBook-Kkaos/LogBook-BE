package com.logbook.backend.logbookbe.domain.issue.model;

import com.logbook.backend.logbookbe.domain.issue.type.Status;
import com.logbook.backend.logbookbe.domain.member.model.Member;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Issue {

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "issue_id", columnDefinition = "VARCHAR(36)")
    private UUID issueId = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private Member assignee;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "issue_title", nullable = false)
    private String issueTitle;

    @Column(name = "issue_description")
    private String issueDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
