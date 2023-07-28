package com.logbook.backend.logbookbe.domain.member.model;

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
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false)
    private int permission_level;

    @Column(nullable = false)
    private int project_id;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private int user_id;
}
