package com.logbook.backend.logbookbe.domain.member.model;

import com.logbook.backend.logbookbe.domain.issue.model.Issue;
import com.logbook.backend.logbookbe.domain.member.type.PermissionLevel;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.user.model.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name="member_id", columnDefinition = "VARCHAR(36)")
    private UUID memberId = UUID.randomUUID();

    @Column(name="permission_level", nullable = false)
    private PermissionLevel permissionLevel;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReleaseNote> releaseNotes = new ArrayList<>();

    public static Member createMember(PermissionLevel permissionLevel, Project project, User user) {
        Member member = new Member();
        member.setPermissionLevel(permissionLevel);
        member.setProject(project);
        member.setUser(user);
        return member;
    }
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues = new ArrayList<>();
}


