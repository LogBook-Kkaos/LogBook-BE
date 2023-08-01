package com.logbook.backend.logbookbe.domain.member.model;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.user.model.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long memberId;

    @Column(name="permission_level", nullable = false)
    private String permissionLevel;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name="role", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}


