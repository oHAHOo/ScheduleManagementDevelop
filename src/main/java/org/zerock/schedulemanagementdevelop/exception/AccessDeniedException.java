package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends ServiceException {
    public AccessDeniedException(String message) {
        super(HttpStatus.FORBIDDEN, message); //403
    }
}
