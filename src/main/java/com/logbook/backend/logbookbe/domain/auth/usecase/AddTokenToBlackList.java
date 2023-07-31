package com.logbook.backend.logbookbe.domain.auth.usecase;

import com.logbook.backend.logbookbe.domain.auth.model.RefreshTokenBlackList;

public interface AddTokenToBlackList {
    RefreshTokenBlackList execute(String token);
}
