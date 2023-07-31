package com.logbook.backend.logbookbe.domain.user.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchResponse {
    private String userName;
    private String email;
}
