package com.logbook.backend.logbookbe.domain.project.repository;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
