package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends ServiceException {
    public InvalidPasswordException(String message) {
        super(HttpStatus.FORBIDDEN, message); //401
    }
}
