package com.logbook.backend.logbookbe.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "존재하지 않는 사용자입니다."),
    INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(403, "EXPIRED_TOKEN", "만료된 토큰입니다."),
    PASSWORD_NOT_MATCH(400, "PASSWORD_NOT_MATCH", "비밀번호가 일치하지 않습니다."),
    EMAIL_DUPLICATE(409, "EMAIL_DUPLICATE", "중복된 이메일입니다."),
    ONLY_SOCIAL_LOGIN(400, "ONLY_SOCIAL_LOGIN", "소셜로그인으로 가입한 계정입니다."),
    MEMBER_NOT_FOUND(404, "MEMBER_NOT_FOUND", "존재하지 않는 멤버입니다."),
    MEMBER_UPDATE_FAILED(500, "MEMBER_UPDATE_FAILED", "멤버 정보 업데이트에 실패했습니다."),
    PROJECT_NOT_FOUND(404, "PROJECT_NOT_FOUND", "존재하지 않는 프로젝트입니다."),
    MEMBER_ALREADY_EXISTS(400, "MEMBER_ALREADY_EXISTS", "멤버가 이미 존재합니다.");


    private final int httpStatus;
    private final String code;
    private final String message;
}