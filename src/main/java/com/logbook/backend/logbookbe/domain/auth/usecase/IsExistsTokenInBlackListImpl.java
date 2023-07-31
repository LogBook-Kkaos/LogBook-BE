package com.logbook.backend.logbookbe.domain.auth.usecase;

import com.logbook.backend.logbookbe.domain.auth.repository.RefreshTokenBlackListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IsExistsTokenInBlackListImpl implements IsExistsTokenInBlackList {
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Override
    public boolean execute(String token) {
        return refreshTokenBlackListRepository.existsByToken(token);
    }
}
