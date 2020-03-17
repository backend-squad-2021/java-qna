package com.codesquad.qna.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public static UnauthorizedException notLogin() {
        return new UnauthorizedException("로그인 해주세요");
    }

    public static UnauthorizedException noMatchUser() {
        return new UnauthorizedException("일치하는 사용자가 존재하지 않습니다");
    }

    public static UnauthorizedException noMatchPassword() {
        return new UnauthorizedException("비밀번호가 일치하지 않습니다");
    }
}
