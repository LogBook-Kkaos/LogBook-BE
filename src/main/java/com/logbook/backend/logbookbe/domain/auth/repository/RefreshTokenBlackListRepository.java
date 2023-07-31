package com.logbook.backend.logbookbe.domain.auth.repository;

import com.logbook.backend.logbookbe.domain.auth.model.RefreshTokenBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenBlackListRepository extends JpaRepository<RefreshTokenBlackList, Long> {
    boolean existsByToken(String token);
}
