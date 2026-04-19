package org.zerock.schedulemanagementdevelop.schedule.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.schedulemanagementdevelop.BaseEntity;
import org.zerock.schedulemanagementdevelop.user.entity.User;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

    // 일정 고유 ID (PK)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일정 제목 (필수, 최대 30자)
    @Column(nullable = false, length = 30)
    private String title;

    // 일정 내용 (필수, 최대 200자)
    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    //일정 수정 메서드
    public void updateSchedule (String title, String content) {
        this.title = title;
        this.content = content;
    }
}
