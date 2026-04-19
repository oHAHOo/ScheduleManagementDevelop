package org.zerock.schedulemanagementdevelop.config;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ServiceException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
