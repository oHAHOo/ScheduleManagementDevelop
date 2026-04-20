package org.zerock.schedulemanagementdevelop.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    //댓글 id
    private final Long id;

    //댓글 작성자 id
    private final Long userId;

    //댓글이 속한 일정 id
    private final Long scheduleId;

    //댓글 내용
    private final String content;

    //생성 시간
    private final LocalDateTime createdAt;

    //수정 시간
    private final LocalDateTime modifiedAt;

    public CreateCommentResponse(Long id, Long userId, Long scheduleId, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userId = userId;
        this.scheduleId = scheduleId;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
