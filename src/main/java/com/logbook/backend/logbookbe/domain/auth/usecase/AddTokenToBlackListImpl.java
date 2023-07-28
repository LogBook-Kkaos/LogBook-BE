package com.logbook.backend.logbookbe.domain.auth.usecase;

import com.logbook.backend.logbookbe.domain.auth.model.RefreshTokenBlackList;
import com.logbook.backend.logbookbe.domain.auth.repository.RefreshTokenBlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddTokenToBlackListImpl implements AddTokenToBlackList {
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Override
    public RefreshTokenBlackList execute(String token) {
        RefreshTokenBlackList result = RefreshTokenBlackList.builder().token(token).build();
        return refreshTokenBlackListRepository.save(result);
    }
}
