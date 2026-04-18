package org.zerock.schedulemanagementdevelop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.dto.ScheduleDto.*;
import org.zerock.schedulemanagementdevelop.entity.Schedule;
import org.zerock.schedulemanagementdevelop.entity.User;
import org.zerock.schedulemanagementdevelop.repository.ScheduleRepository;
import org.zerock.schedulemanagementdevelop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository  scheduleRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateScheduleResponse saveSchedule(CreateScheduleRequest createScheduleRequest){
        User user = userRepository.findById(createScheduleRequest.getUserId()).orElseThrow( () -> new IllegalStateException("User not found"));
        Schedule schedule = new Schedule(
                user,
                createScheduleRequest.getTitle(),
                createScheduleRequest.getContent()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getId(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetScheduleResponse findById(Long id) {
        // ID에 해당하는 일정 조회, 없으면 예외 발생
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("Schedule not found"));

        return new GetScheduleResponse(
                schedule.getId(),
                schedule.getUser().getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetScheduleResponse> findAll(Long userId) {
        List<Schedule> schedules;

        if (userId == null) {
            schedules = scheduleRepository.findAll();
        } else {
            schedules = scheduleRepository.findByUser_Id(userId);
        }

        List<GetScheduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetScheduleResponse dto = new GetScheduleResponse(
                    schedule.getId(),
                    schedule.getUser().getId(),
                    schedule.getUser().getUsername(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional
    public UpdateScheduleResponse updateSchedule(Long id, UpdateScheduleRequest updateScheduleRequest) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("Schedule not found"));
        schedule.updateSchedule(updateScheduleRequest.getTitle(),updateScheduleRequest.getContent());
        return new UpdateScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getModifiedAt());
        }

    @Transactional
    public void deleteSchedule(Long id) {
        //일정이 없으면 예외
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalStateException("Schedule not found");
        }
        scheduleRepository.deleteById(id);
    }
}



