package org.zerock.schedulemanagementdevelop.dto.UserDto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String username;
    private String email;

    @Size(min = 8,message = "비밀번호는 8글자 이상이어야합니다.")
    private String password; //비밀번호 추가

    public CreateUserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
