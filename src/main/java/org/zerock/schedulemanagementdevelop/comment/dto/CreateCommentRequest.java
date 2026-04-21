package org.zerock.schedulemanagementdevelop.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @NotBlank
    private String content; //댓글 내용

    public CreateCommentRequest(String content) {
        this.content = content;
    }
}
