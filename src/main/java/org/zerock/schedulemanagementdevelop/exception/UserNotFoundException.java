package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

//사용자을 찾을 수 없을 때 (404 Not_Found)
public class UserNotFoundException extends ServiceException {
    public UserNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
