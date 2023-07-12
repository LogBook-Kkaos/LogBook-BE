package com.logbook.backend.logbookbe.domain.user.repository;

import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
