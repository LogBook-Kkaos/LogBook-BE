package com.logbook.backend.logbookbe.domain.issue.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    할일,
    진행중,
    완료
}
