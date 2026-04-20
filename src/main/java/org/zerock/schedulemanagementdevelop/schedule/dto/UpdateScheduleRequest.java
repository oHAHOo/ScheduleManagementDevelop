package org.zerock.schedulemanagementdevelop.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    //일정 제목
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 10, message = "제목은 10글자 이내로 입력하세요.")
    private String title;

    //일정 내용
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public UpdateScheduleRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
