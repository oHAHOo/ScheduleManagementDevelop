package org.zerock.schedulemanagementdevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.exception.AccessDeniedException;
import org.zerock.schedulemanagementdevelop.exception.ScheduleNotFoundException;
import org.zerock.schedulemanagementdevelop.exception.UserNotFoundException;
import org.zerock.schedulemanagementdevelop.schedule.dto.*;
import org.zerock.schedulemanagementdevelop.schedule.entity.Schedule;
import org.zerock.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import org.zerock.schedulemanagementdevelop.user.entity.User;
import org.zerock.schedulemanagementdevelop.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //일정 생성
    @Transactional
    public CreateScheduleResponse saveSchedule(CreateScheduleRequest createScheduleRequest, Long userId) {

        //유저 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
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

    //일정 단일 조회
    @Transactional(readOnly = true)
    public GetScheduleResponse findById(Long id) {
        // ID에 해당하는 일정 조회, 없으면 예외 발생
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

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

    //일정 목록 페이징 조회
    //userId가 있으면 해당 유저 일정만 조회
    @Transactional(readOnly = true)
    public Page<SchedulePageResponse> findAll(Long userId, Pageable pageable) {
        return scheduleRepository.findAllSchedules(userId, pageable);
    }

    //일정 수정
    @Transactional
    public UpdateScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest updateScheduleRequest, Long userId) {

        //일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        //권한 확인
        if (!schedule.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("수정 권한이 없습니다.");
        }
        schedule.updateSchedule(updateScheduleRequest.getTitle(), updateScheduleRequest.getContent());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getModifiedAt());
    }

    //일정 삭제
    @Transactional
    public void deleteSchedule(Long scheduleId, Long userId) {

        //일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        //권한 조회
        if (!schedule.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}



