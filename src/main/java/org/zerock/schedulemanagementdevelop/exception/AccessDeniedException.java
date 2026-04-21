package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

//접근 권한이 없을 때 (403 Forbidden)
public class AccessDeniedException extends ServiceException {
    public AccessDeniedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
