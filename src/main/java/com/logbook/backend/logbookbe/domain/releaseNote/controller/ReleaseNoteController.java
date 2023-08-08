package com.logbook.backend.logbookbe.domain.releaseNote.controller;

import com.logbook.backend.logbookbe.domain.releaseNote.dto.ReleaseNoteDTO;
import com.logbook.backend.logbookbe.domain.releaseNote.model.ReleaseNote;
import com.logbook.backend.logbookbe.domain.releaseNote.repository.ReleaseNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/release-notes/create")
public class ReleaseNoteController {

    @Autowired
    private ReleaseNoteRepository releaseNoteRepository;

    @PostMapping
    public ResponseEntity<String> createReleaseNote(@RequestBody ReleaseNoteDTO releaseNoteDTO) {
        ReleaseNote releaseNote = new ReleaseNote();
        releaseNote.setProjectId(releaseNoteDTO.getProjectId());
        releaseNote.setUpdateDocumentsId(releaseNoteDTO.getUpdateDocumentsId());
        releaseNote.setReleaseTitle(releaseNoteDTO.getReleaseTitle());
        releaseNote.setReleaseContent(releaseNoteDTO.getReleaseContent());
        releaseNote.setCreationDate(releaseNoteDTO.getCreationDate());
        releaseNote.setVersion(releaseNoteDTO.getVersion());
        releaseNote.setIsImportant(releaseNoteDTO.isImportant());
        releaseNote.setIsPublic(releaseNoteDTO.isPublic());
        releaseNote.setStatus(releaseNoteDTO.getStatus());

        releaseNoteRepository.save(releaseNote);

        return ResponseEntity.ok("Release note created successfully.");
    }
}