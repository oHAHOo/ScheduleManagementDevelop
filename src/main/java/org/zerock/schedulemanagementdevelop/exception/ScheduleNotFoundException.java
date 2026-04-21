package org.zerock.schedulemanagementdevelop.exception;

import org.springframework.http.HttpStatus;

//일정을 찾을 수 없을 때 (404 Not_Found)
public class ScheduleNotFoundException extends ServiceException {
    public ScheduleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);  //404
    }
}
