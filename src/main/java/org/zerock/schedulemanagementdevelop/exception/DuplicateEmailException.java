package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

//이메일이 중복일 경우 (409 CONFLICT)
public class DuplicateEmailException extends ServiceException {
    public DuplicateEmailException(String message) {
        super(HttpStatus.CONFLICT, message); //409
    }
}
