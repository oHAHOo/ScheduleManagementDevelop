package org.zerock.schedulemanagementdevelop.dto.ScheduleDto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    // 일정 제목
    private String title;

    // 일정 내용
    private String content;

    // 작성자 이름
    private String userName;

    public CreateScheduleRequest(String userName, String title, String content) {
        this.userName = userName;
        this.title = title;
        this.content = content;

    }
}
