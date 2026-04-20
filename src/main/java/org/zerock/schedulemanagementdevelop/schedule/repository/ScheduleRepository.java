package org.zerock.schedulemanagementdevelop.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.schedulemanagementdevelop.schedule.dto.SchedulePageResponse;
import org.zerock.schedulemanagementdevelop.schedule.entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("""
    SELECT new org.zerock.schedulemanagementdevelop.schedule.dto.SchedulePageResponse(
        s.id,
        s.title,
        s.content,
        COUNT(c),
        s.createdAt,
        s.modifiedAt,
        u.username
    )
    FROM Schedule s
    JOIN s.user u
    LEFT JOIN s.comments c
    GROUP BY s.id, u.username
""")
    Page<SchedulePageResponse> findAllSchedules(Long userId, Pageable pageable);
}
