package org.zerock.schedulemanagementdevelop.dto.UserDto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String username;
    private String email;

    public CreateUserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
