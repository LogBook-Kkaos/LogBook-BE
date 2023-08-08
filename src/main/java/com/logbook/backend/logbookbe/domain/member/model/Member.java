package com.logbook.backend.logbookbe.domain.member.model;

import com.logbook.backend.logbookbe.domain.member.type.PermissionLevel;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.model.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public static Member createMember(PermissionLevel permissionLevel, Project project, User user) {
        Member member = new Member();
        member.setPermissionLevel(permissionLevel);
        member.setProject(project);
        member.setUser(user);
        return member;
    }
}


