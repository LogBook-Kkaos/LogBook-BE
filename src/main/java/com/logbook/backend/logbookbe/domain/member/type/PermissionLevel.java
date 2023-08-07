package com.logbook.backend.logbookbe.domain.member.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionLevel {
    관리자,
    편집자,
    뷰어
}
