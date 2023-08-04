package com.logbook.backend.logbookbe.domain.document.service;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.document.repository.DocumentRepository;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
import com.logbook.backend.logbookbe.domain.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    @Autowired
    private final ProjectRepository projectRepository;


    @Autowired
    public DocumentService(DocumentRepository documentRepository, ProjectRepository projectRepository) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
    }



    public List<getAllDocumentRequest> getAllDocuments(UUID projectId) {
        List<Document> documents = documentRepository.findByProjectProjectId(projectId);
        List<getAllDocumentRequest> documentDTOs = new ArrayList<>();
        for (Document document : documents) {
            getAllDocumentRequest documentDTO = new getAllDocumentRequest();
            documentDTO.setDocumentId(document.getDocumentId());
            documentDTO.setDocumentTitle(document.getDocumentTitle());
            documentDTO.setCreationDate(document.getCreationDate());
            documentDTOs.add(documentDTO);
        }
        return documentDTOs;
    }
    public getDocumentRequest getDocument(UUID documentId){
        Document document = documentRepository.findByDocumentId(documentId);
        getDocumentRequest documentDTO = new getDocumentRequest();

        documentDTO.setDocumentId(document.getDocumentId());
        documentDTO.setDocumentTitle(document.getDocumentTitle());
        documentDTO.setDocumentContent(document.getDocumentContent());
        documentDTO.setCreationDate(document.getCreationDate());

        return documentDTO;

    }

    public boolean createDocument(createDocumentRequest documentDto, Integer projectId){
        Document document = new Document();
        document.setDocumentTitle(documentDto.getDocumentTitle());
        document.setDocumentContent(documentDto.getDocumentContent());
        document.setCreationDate(Timestamp.from(Instant.now()));
        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);
        if (projectOptional.isPresent()) {
            document.setProject(projectOptional.get());
        } else {
            throw new RuntimeException("잘못된 project입니다.");
        }

        documentRepository.save(document);

        return true;
    }




}
