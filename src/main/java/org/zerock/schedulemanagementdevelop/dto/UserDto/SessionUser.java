package org.zerock.schedulemanagementdevelop.dto.UserDto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long id;
    private final String username;

    public  SessionUser(Long id, String username){
        this.id = id;
        this.username = username;
    }
}
