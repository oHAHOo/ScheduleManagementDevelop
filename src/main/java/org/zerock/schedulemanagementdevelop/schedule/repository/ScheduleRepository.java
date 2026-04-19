package org.zerock.schedulemanagementdevelop.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.schedulemanagementdevelop.schedule.entity.Schedule;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findByUser_Id(Long userId);
}
