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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "CHAR(10)")
    @Enumerated(EnumType.STRING)
    private Vendor vendor;

    @Column()
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column()
    private String department;

    @Column(nullable = false)
    private String password;

}
