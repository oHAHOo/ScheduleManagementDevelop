package org.zerock.schedulemanagementdevelop.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponse {

    //사용자 고유 ID
    private final Long id;

    //사용자 이름
    private final String username;

    //사용자 이메일
    private final String email;

    //사용자 정보 생성 시간
    private final LocalDateTime createdAt;

    //사용자 정보 수정 시간
    private final LocalDateTime modifiedAt;

    public GetUserResponse(Long id, String username, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
