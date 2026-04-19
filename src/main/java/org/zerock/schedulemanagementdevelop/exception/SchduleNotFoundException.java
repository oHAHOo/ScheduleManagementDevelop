package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

public class SchduleNotFoundException extends ServiceException {
    public SchduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);  //404
    }
}
