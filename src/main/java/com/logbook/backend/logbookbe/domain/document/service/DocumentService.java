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

            List<DocumentFile> documentFiles = document.getDocumentFiles();
            if (!documentFiles.isEmpty()) {
                DocumentFile firstDocumentFile = documentFiles.get(0);
                documentDTO.setImageUrl(firstDocumentFile.getImageUrl());
            }
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
    public boolean deleteDocument(UUID documentId) {
        Optional<Document> documentOptional = documentRepository.findByDocumentId(documentId);

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            List<DocumentFile> documentFiles = document.getDocumentFiles();
            Project project = document.getProject();

            if (documentFiles != null && !documentFiles.isEmpty()) {
                documentFilesRepository.deleteAll(documentFiles);
            }
            project.getDocuments().remove(document);
            documentRepository.delete(document);
            return true;
        } else {
            throw new RuntimeException("잘못된 document입니다.");
        }
    }

    public boolean updateDocument(UUID documentId, createDocumentRequest documentDto) {
        Optional<Document> documentOptional = documentRepository.findByDocumentId(documentId);

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            document.setDocumentTitle(documentDto.getDocumentTitle());
            document.setDocumentContent(documentDto.getDocumentContent());
            document.setCreationDate(Timestamp.from(Instant.now()));
            documentRepository.save(document);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateDocumentFiles(UUID documentId, List<String> imageUrlList) {
        Optional<Document> documentOptional = documentRepository.findByDocumentId(documentId);

        if (documentOptional.isPresent()) {
            Document document = documentOptional.get();
            List<DocumentFile> documentFiles = document.getDocumentFiles();

            if (documentFiles != null && !documentFiles.isEmpty()) {
                documentFilesRepository.deleteAll(documentFiles);
            }

            List<DocumentFile> newDocumentFiles = new ArrayList<>();
            for (String imageUrl : imageUrlList) {
                DocumentFile documentFile = new DocumentFile();
                documentFile.setImageUrl(imageUrl);
                documentFile.setDocument(document);
                newDocumentFiles.add(documentFile);
            }
            newDocumentFiles = documentFilesRepository.saveAll(newDocumentFiles);
            document.setDocumentFiles(newDocumentFiles);

            return true;
        } else {
            throw new RuntimeException("잘못된 document입니다.");
        }
    }
    public List<getAllDocumentRequest> searchDocuments(UUID projectId, String searchString){

        List<Document> documents = documentRepository.findByProjectProjectIdAndDocumentTitleContaining(projectId, searchString);
        List<getAllDocumentRequest> documentDTOs = new ArrayList<>();
        for (Document document : documents) {
            getAllDocumentRequest documentDTO = new getAllDocumentRequest();
            documentDTO.setDocumentId(document.getDocumentId());
            documentDTO.setDocumentTitle(document.getDocumentTitle());
            documentDTO.setCreationDate(document.getCreationDate());

            List<DocumentFile> documentFiles = document.getDocumentFiles();
            if (!documentFiles.isEmpty()) {
                DocumentFile firstDocumentFile = documentFiles.get(0);
                documentDTO.setImageUrl(firstDocumentFile.getImageUrl());
            }
            documentDTOs.add(documentDTO);
        }
        return documentDTOs;

    }

}
