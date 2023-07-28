package com.logbook.backend.logbookbe.domain.project.controller;

import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{project_id}")
    public Project getProjectById(@PathVariable("project_id") Integer projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{project_id}")
    public Project updateProject(@PathVariable("project_id") Integer projectId, @RequestBody Project project) {
        project.setProject_id(projectId);
        return projectService.updateProject(project);
    }

    @DeleteMapping("/{project_id}")
    public void deleteProject(@PathVariable("project_id") Integer projectId) {
        projectService.deleteProject(projectId);
    }
}
