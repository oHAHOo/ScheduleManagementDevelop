package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

// 인증되지 않은 사용자가 접근할 때 (401 Unauthorized)
public class UnauthorizedException extends ServiceException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
