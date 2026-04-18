package org.zerock.schedulemanagementdevelop.dto.ScheduleDto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private Long userId;
    // 일정 제목
    private String title;
    // 일정 내용
    private String content;

    public CreateScheduleRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;

    }
}
