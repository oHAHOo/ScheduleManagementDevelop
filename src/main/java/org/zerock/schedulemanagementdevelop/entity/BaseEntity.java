package org.zerock.schedulemanagementdevelop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    // 엔티티 생성 시 자동으로 현재 시간이 저장됨
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // 엔티티 수정 시 자동으로 현재 시간이 업데이트됨
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
