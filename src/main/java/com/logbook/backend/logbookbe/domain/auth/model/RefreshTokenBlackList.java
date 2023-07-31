package com.logbook.backend.logbookbe.domain.auth.model;

import javax.persistence.*;

import com.logbook.backend.logbookbe.domain.shared.entity.BaseTimeEntity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name = "refreshtoken_blacklist")
public class RefreshTokenBlackList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;
}
