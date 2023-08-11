package com.logbook.backend.logbookbe.domain.project.controller;

import com.logbook.backend.logbookbe.domain.project.controller.dto.DeleteResponse;
import com.logbook.backend.logbookbe.domain.project.controller.dto.GetProjectResponse;
import com.logbook.backend.logbookbe.domain.project.controller.dto.PublicProjectResponse;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.service.ProjectService;
import com.logbook.backend.logbookbe.domain.user.exception.UserNotFoundException;
import com.logbook.backend.logbookbe.global.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    @Operation(summary = "모든 프로젝트 목록 조회", description = "모든 프로젝트의 목록을 조회합니다.")
    public List<PublicProjectResponse> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<PublicProjectResponse> projectResponses = new ArrayList<>();

        for (Project project : projects) {
            if (project.isPublic()) {
                PublicProjectResponse projectResponse = new PublicProjectResponse();
                projectResponse.setProjectName(project.getProjectName());
                projectResponse.setProjectDescription(project.getProjectDescription());

                projectResponses.add(projectResponse);
            }
        }
        return projectResponses;
    }

    @GetMapping("/{projectId}")
    @Operation(summary = "프로젝트 상세 조회", description = "특정 프로젝트의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<GetProjectResponse> getProject(@PathVariable("projectId") UUID projectId) {
        GetProjectResponse projectDTO = projectService.getProject(projectId);
        return new ResponseEntity<GetProjectResponse>(projectDTO,HttpStatus.OK);
    }


    public Project getProjectById(@PathVariable("projectId") UUID projectId) {
        return projectService.getProjectById(projectId);
    }

    @PostMapping
    @Operation(summary = "프로젝트 생성", description = "새로운 프로젝트를 생성합니다.")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class)))
    public UUID createProject(@RequestBody Project project) {
        System.out.println("Received project with isPublic: " + project);
        Project createdProject = projectService.createProject(project);
        return createdProject.getProjectId();
    }

    @PutMapping("/{projectId}")
    @Operation(summary = "프로젝트 수정", description = "기존 프로젝트를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Project updateProject(@PathVariable("projectId") UUID projectId, @RequestBody Project updatedProject) {
        Project existingProject = projectService.getProjectById(projectId);
        if (existingProject == null) {
            throw new UserNotFoundException();
        }

        existingProject.setProjectName(updatedProject.getProjectName());
        existingProject.setProjectDescription(updatedProject.getProjectDescription());
        existingProject.setPublic(updatedProject.isPublic());

        return projectService.updateProject(existingProject);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "프로젝트 삭제", description = "특정 프로젝트를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public DeleteResponse deleteProject(@PathVariable("projectId") UUID projectId) {
        return projectService.deleteProject(projectId);
    }
}
