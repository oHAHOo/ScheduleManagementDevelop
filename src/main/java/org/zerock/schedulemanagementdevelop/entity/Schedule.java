package org.zerock.schedulemanagementdevelop.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{

    // 일정 고유 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 작성자 이름 (필수)
    @Column(nullable = false)
    private String userName;

    // 일정 제목 (필수, 최대 30자)
    @Column(nullable = false, length = 30)
    private String title;

    // 일정 내용 (필수, 최대 200자)
    @Column(nullable = false, length = 200)
    private String content;

    public Schedule(String userName, String title, String content) {
        this.userName = userName;
        this.title = title;
        this.content = content;
    }

    //일정 수정 메서드
    public void updateSchedule (String title, String userName) {
        this.title = title;
        this.userName = userName;
    }
}
