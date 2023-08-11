package com.logbook.backend.logbookbe.domain.project.service;

import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.project.controller.dto.DeleteResponse;
import com.logbook.backend.logbookbe.domain.project.controller.dto.DocumentRequest;
import com.logbook.backend.logbookbe.domain.project.controller.dto.GetProjectResponse;
import com.logbook.backend.logbookbe.domain.project.controller.dto.ReleaseNoteRequest;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public GetProjectResponse getProject(UUID projectId) {

        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);
        Project project;
        GetProjectResponse projectDTO = new GetProjectResponse();

        if (projectOptional.isPresent()) {
            project = projectOptional.get();
        } else {
            throw new RuntimeException("잘못된 project입니다.");
        }


        projectDTO.setProjectId(project.getProjectId());
        projectDTO.setProjectName(project.getProjectName());
        projectDTO.setProjectDescription(project.getProjectDescription());
        projectDTO.setPublic(project.isPublic());

        List<ReleaseNote> releaseNotes = project.getReleaseNotes();
        List<ReleaseNoteRequest> releaseNoteDTOs = new ArrayList<>();

        for (ReleaseNote releaseNote : releaseNotes) {
            ReleaseNoteRequest releaseNoteDTO = new ReleaseNoteRequest();
            releaseNoteDTO.setReleaseTitle(releaseNote.getReleaseTitle());
            releaseNoteDTO.setReleaseNoteId(releaseNote.getReleaseNoteId());

            releaseNoteDTOs.add(releaseNoteDTO);
        }

        projectDTO.setReleaseNotes(releaseNoteDTOs);

        List<Document> documents = project.getDocuments();
        List<DocumentRequest> documentDTOs = new ArrayList<>();

        for (Document document : documents) {
            DocumentRequest documentDTO = new DocumentRequest();
            documentDTO.setDocumentId(document.getDocumentId());
            documentDTO.setDocumentTitle(document.getDocumentTitle());

            documentDTOs.add(documentDTO);
        }

        projectDTO.setDocuments(documentDTOs);

        return projectDTO;

    }

    public Project getProjectById(UUID projectId) {
        return projectRepository.findById(projectId)
                .orElse(null);
    }

    public Project createProject(Project project) {
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
