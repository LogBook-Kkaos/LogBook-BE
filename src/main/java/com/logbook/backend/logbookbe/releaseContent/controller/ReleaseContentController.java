package com.logbook.backend.logbookbe.releaseContent.controller;

import com.logbook.backend.logbookbe.releaseContent.dto.ReleaseContentDto;
import com.logbook.backend.logbookbe.releaseContent.model.ReleaseContent;
import com.logbook.backend.logbookbe.releaseContent.repository.ReleaseContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/release-notes/release-content")
public class ReleaseContentController {

    @Autowired
    private ReleaseContentRepository releaseContentRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createReleaseContent(@RequestBody ReleaseContentDto releaseContentDto) {
        ReleaseContent releaseContent = new ReleaseContent();
        releaseContent.setReleaseNoteId(releaseContentDto.getReleaseNoteId());
        releaseContent.setDocumentId(releaseContentDto.getDocumentId());
        releaseContent.setReleaseSummary(releaseContentDto.getReleaseSummary());
        releaseContent.setCategory(releaseContentDto.getCategory());
        releaseContent.setDocumentLink(releaseContentDto.getDocumentLink());

        releaseContentRepository.save(releaseContent);

        return ResponseEntity.ok("Release content created successfully.");
    }


}
