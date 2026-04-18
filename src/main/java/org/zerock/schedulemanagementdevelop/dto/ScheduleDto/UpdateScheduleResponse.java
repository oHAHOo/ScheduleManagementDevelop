package org.zerock.schedulemanagementdevelop.dto.ScheduleDto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    // 일정 고유 ID
    private final Long id;

    // 수정된 일정 제목
    private final String title;

    // 수정된 일정 내용
    private final String content;

    // 수정된 시간 (마지막 수정 시각)
    private final LocalDateTime modifiedAt;


    public UpdateScheduleResponse(Long id, String title, String content, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }
}
