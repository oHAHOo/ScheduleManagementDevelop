package org.zerock.schedulemanagementdevelop.dto.UserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 10,message = "이름은 최대 10글자입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일형식이 아닙니다.")
    private String email;


    public UpdateUserRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
