package org.zerock.schedulemanagementdevelop.config;

import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException(String message) {
        super(HttpStatus.CONFLICT, message); //409
    }
}
