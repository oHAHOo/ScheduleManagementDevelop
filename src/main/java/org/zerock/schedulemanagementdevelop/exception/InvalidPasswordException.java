package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

//비밀번호가 일치하지 않을 때 (401 Unauthorized)
public class InvalidPasswordException extends ServiceException {
    public InvalidPasswordException(String message) {
        super(HttpStatus.UNAUTHORIZED, message); //401
    }
}
