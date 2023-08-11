package com.logbook.backend.logbookbe.domain.document.service;

import com.logbook.backend.logbookbe.domain.document.dto.createDocumentFilesRequest;
import com.logbook.backend.logbookbe.domain.document.dto.createDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getAllDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.dto.getDocumentRequest;
import com.logbook.backend.logbookbe.domain.document.model.Document;
import com.logbook.backend.logbookbe.domain.document.model.DocumentFile;
import com.logbook.backend.logbookbe.domain.document.repository.DocumentFilesRepository;
import com.logbook.backend.logbookbe.domain.document.repository.DocumentRepository;
import com.logbook.backend.logbookbe.domain.project.model.Project;
import com.logbook.backend.logbookbe.domain.project.repository.ProjectRepository;
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
    private final DocumentFilesRepository documentFilesRepository;


    @Autowired
    public DocumentService(DocumentRepository documentRepository, ProjectRepository projectRepository, DocumentFilesRepository documentFilesRepository) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.documentFilesRepository = documentFilesRepository;
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
        Optional<Document> documentOp= documentRepository.findByDocumentId(documentId);
        Document document =  documentOp.get();
        getDocumentRequest documentDTO = new getDocumentRequest();

        documentDTO.setDocumentId(document.getDocumentId());
        documentDTO.setDocumentTitle(document.getDocumentTitle());
        documentDTO.setDocumentContent(document.getDocumentContent());
        documentDTO.setCreationDate(document.getCreationDate());

        return documentDTO;

    }

    public UUID createDocument(createDocumentRequest documentDto, UUID projectId){
        Document document = new Document();
        document.setDocumentTitle(documentDto.getDocumentTitle());
        document.setDocumentContent(documentDto.getDocumentContent());
        document.setCreationDate(Timestamp.from(Instant.now()));
        Optional<Project> projectOptional = projectRepository.findByProjectId(projectId);
        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            document.setProject(project);
            document = documentRepository.save(document);
            project.getDocuments().add(document);
        } else {
            throw new RuntimeException("잘못된 project입니다.");
        }
        return document.getDocumentId();
    }

    public boolean createDocumentFiles(List<String> imageUrlList, UUID documentId) {
        Optional<Document> documentOptional = documentRepository.findByDocumentId(documentId);

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            List<DocumentFile> documentFiles = new ArrayList<>();

            for (String imageUrl : imageUrlList) {
                System.out.println(imageUrl);
                DocumentFile documentFile = new DocumentFile();
                documentFile.setImageUrl(imageUrl);
                documentFile.setDocument(document);
                documentFiles.add(documentFile);
            }

            documentFiles = documentFilesRepository.saveAll(documentFiles);
            document.getDocumentFiles().addAll(documentFiles);
            return true;

        } else {
            throw new RuntimeException("잘못된 document입니다.");
        }
    }
}
