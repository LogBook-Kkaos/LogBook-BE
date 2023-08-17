package com.logbook.backend.logbookbe.releaseContent.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    Fixed,
    Feature,
    Deprecated,
    Changed,
    General

}
