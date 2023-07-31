package com.logbook.backend.logbookbe.domain.user.repository;

import com.logbook.backend.logbookbe.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByEmailContaining(String email);
}
