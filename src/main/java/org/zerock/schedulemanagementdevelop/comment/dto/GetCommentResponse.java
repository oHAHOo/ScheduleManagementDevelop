package org.zerock.schedulemanagementdevelop.comment.dto;

import java.time.LocalDateTime;

public record GetCommentResponse(Long commentId, Long userId, Long scheduleId, String content, LocalDateTime createdAt,
                                 LocalDateTime modifiedAt) {
}
