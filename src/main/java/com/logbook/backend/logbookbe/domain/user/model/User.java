package com.logbook.backend.logbookbe.domain.user.model;

import javax.persistence.*;
import java.util.UUID;

import lombok.*;
import org.hibernate.annotations.Type;

import com.logbook.backend.logbookbe.domain.user.type.Vendor;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    @Column(name = "user_id", columnDefinition = "VARCHAR(36)")
    private UUID userId = UUID.randomUUID();

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
