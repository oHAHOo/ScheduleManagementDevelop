package org.zerock.schedulemanagementdevelop.dto.UserDto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private String username;
    private String email;

    public UpdateUserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
