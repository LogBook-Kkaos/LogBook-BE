package com.logbook.backend.logbookbe.domain.member.controller.dto;

public class MemberNotFoundProject extends RuntimeException {

    public MemberNotFoundProject(String message) {
        super(message);
    }
}