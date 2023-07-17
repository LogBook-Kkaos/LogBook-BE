package com.logbook.backend.logbookbe.domain.user.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column()
    private String department;

    @Column(nullable = false)
    private String password;

}
