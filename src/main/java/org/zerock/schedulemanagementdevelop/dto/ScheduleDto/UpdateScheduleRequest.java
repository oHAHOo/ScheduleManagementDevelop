package org.zerock.schedulemanagementdevelop.dto.ScheduleDto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    // 수정할 일정 제목
    private String title;

    // 수정할 일정 내용
    private String content;

    public UpdateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
