package com.logbook.backend.logbookbe.domain.project.service;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Integer projectId) {
        return projectRepository.findById(projectId)
                .orElse(null);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Integer projectId) {
        projectRepository.deleteById(projectId);
    }
}
