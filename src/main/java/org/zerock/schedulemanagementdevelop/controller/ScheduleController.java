package org.zerock.schedulemanagementdevelop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedulemanagementdevelop.exception.UnauthorizedException;
import org.zerock.schedulemanagementdevelop.dto.UserDto.SessionUser;
import org.zerock.schedulemanagementdevelop.service.ScheduleService;
import org.zerock.schedulemanagementdevelop.dto.ScheduleDto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //일정 등록
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> saveSchedule(@Valid @RequestBody CreateScheduleRequest createScheduleRequest, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");
        if(sessionUser==null){
            throw new UnauthorizedException("로그인이 필요합니다");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(createScheduleRequest,sessionUser.getId()));
    }

    //단일 일정 조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetScheduleResponse> getOneSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findById(id));
    }

    //전체 일정 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetScheduleResponse>> getAllSchedules(@RequestParam(required = false) Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(userId));
    }

    //일정 수정
    @PutMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateScheduleRequest updateScheduleRequest,
            HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");
        if(sessionUser==null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, updateScheduleRequest, sessionUser.getId()));
    }

    //일정 삭제
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");

        if(sessionUser==null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        scheduleService.deleteSchedule(id, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
