package com.logbook.backend.logbookbe.domain.user.model;

import com.logbook.backend.logbookbe.domain.user.type.Vendor;
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
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="vendor", columnDefinition = "CHAR(10)")
    @Enumerated(EnumType.STRING)
    private Vendor vendor;

    @Column(name="user_name")
    private String userName;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="department")
    private String department;

    @Column(name="password", nullable = false)
    private String password;

}
