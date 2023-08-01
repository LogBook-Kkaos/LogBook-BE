package com.logbook.backend.logbookbe.global.cron;

import com.logbook.backend.logbookbe.domain.auth.repository.RefreshTokenBlackListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class RefreshTokenBlackListCron {
    private final RefreshTokenBlackListRepository refreshTokenBlackListRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAfter30d() {
        LocalDateTime now = LocalDateTime.now();
        refreshTokenBlackListRepository.findAll()
                .stream()
                .filter(token -> token.getCreatedAt().plusDays(30).isBefore(now))
                .forEach(refreshTokenBlackListRepository::delete);

        log.info("Delete expired refresh token in blacklist successfully.");
    }
}
