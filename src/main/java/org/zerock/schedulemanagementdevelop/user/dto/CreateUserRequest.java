package org.zerock.schedulemanagementdevelop.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 10,message = "이름은 최대 10글자입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "올바른 이메일형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8,message = "비밀번호는 8글자 이상이어야합니다.")
    private String password; //비밀번호 추가

    public CreateUserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
