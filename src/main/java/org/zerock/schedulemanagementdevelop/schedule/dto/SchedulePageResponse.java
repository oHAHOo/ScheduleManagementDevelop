package org.zerock.schedulemanagementdevelop.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulePageResponse {

    //일정 ID
    private final Long id;

    //일정 제목
    private final String title;

    //일정 내용
    private final String content;

    //일정에 달린 댓글의 수
    private final Long commentCount;

    //일정 생성 시간
    private final LocalDateTime createdAt;

    //일정 수정 시간
    private final LocalDateTime modifiedAt;

    // 작성자 이름
    private final String userName;





    public SchedulePageResponse(
            Long id,
            String title,
            String content,
            Long commentCount,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            String userName
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userName = userName;
    }
}
