package org.zerock.schedulemanagementdevelop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.dto.*;
import org.zerock.schedulemanagementdevelop.entity.Schedule;
import org.zerock.schedulemanagementdevelop.repository.ScheduleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private ScheduleRepository  scheduleRepository;

    @Transactional
    public CreateScheduleResponse saveSchedule(CreateScheduleRequest createScheduleRequest){
        Schedule schedule = new Schedule(
                createScheduleRequest.getUserName(),
                createScheduleRequest.getTitle(),
                createScheduleRequest.getContent()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUserName(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional
    public GetOneScheduleResponse findById(Long id) {
        // ID에 해당하는 일정 조회, 없으면 예외 발생
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("Schedule not found"));

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getUserName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetAllScheduleResponse> findAll(String userName) {
        List<Schedule> schedules;
        if(userName == null){// userName이 없으면 전체 조회
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        }
        else{// userName이 있으면 해당 작성자의 일정만 조회
            schedules = scheduleRepository.findByUserNameOrderByModifiedAtDesc(userName);
        }

        List<GetAllScheduleResponse> dtos = new ArrayList<>();
        for(Schedule schedule : schedules){
            GetAllScheduleResponse dto = new GetAllScheduleResponse(
                    schedule.getId(),
                    schedule.getUserName(),
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
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("schedule not found"));
        schedule.updateSchedule(updateScheduleRequest.getTitle(),updateScheduleRequest.getContent());
        return new UpdateScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getModifiedAt());
        }

    @Transactional
    public void deleteSchedule(Long id) {
        //일정이 없으면 예외
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalStateException("schedule not found"));

        scheduleRepository.deleteById(id);
    }
}



