package com.logbook.backend.logbookbe.domain.project.service;

import com.logbook.backend.logbookbe.domain.project.controller.dto.DeleteResponse;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElse(null);
    }

    public Project createProject(Project project) {
        project.setMemberCount(1);
        return projectRepository.save(project);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    public DeleteResponse deleteProject(UUID projectId) {
        projectRepository.deleteById(projectId);
        return new DeleteResponse(projectId, "Project deleted successfully");
    }
}
