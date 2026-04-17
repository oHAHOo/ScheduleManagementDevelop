package org.zerock.schedulemanagementdevelop.dto.ScheduleDto;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CreateScheduleResponse {
    // 일정 고유 ID
    private final Long id;

    // 일정 제목
    private final String title;

    // 일정 내용
    private final String content;

    // 작성자 이름
    private final String userName;

    // 일정 생성 시간
    private final LocalDateTime createdAt;

    // 일정 수정 시간
    private final LocalDateTime modifiedAt;

    public CreateScheduleResponse(Long id, String userName, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
