package com.logbook.backend.logbookbe.domain.project.repository;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Optional<Project> findByProjectId(UUID projectId);

    Optional<Project> findById(UUID projectId);
}
