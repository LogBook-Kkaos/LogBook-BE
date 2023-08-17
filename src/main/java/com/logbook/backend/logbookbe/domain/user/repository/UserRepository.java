package com.logbook.backend.logbookbe.domain.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.logbook.backend.logbookbe.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByEmailContaining(String email);

    Optional<User> findByUserId(UUID id);
}
