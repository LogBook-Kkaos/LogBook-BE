package com.logbook.backend.logbookbe.domain.auth.usecase;

public interface IsExistsTokenInBlackList {
    boolean execute(String token);
}
