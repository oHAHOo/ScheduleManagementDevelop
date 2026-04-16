package org.zerock.schedulemanagementdevelop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedulemanagementdevelop.dto.*;
import org.zerock.schedulemanagementdevelop.service.ScheduleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private ScheduleService scheduleService;

    //일정 등록
    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> createSchedule(@RequestBody CreateScheduleRequest createScheduleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.saveSchedule(createScheduleRequest));
    }

    //단일 일정 조회
    @GetMapping("/schedules/{id}")
    public ResponseEntity<GetOneScheduleResponse> getOneSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findById(id));
    }

    //전체 일정 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<GetAllScheduleResponse>> getAllSchedules(@RequestParam(required = false) String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(userName));
    }

    //일정 수정
    @PutMapping("/schedules/{id}")
    public ResponseEntity<UpdateScheduleResponse> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequest updateScheduleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, updateScheduleRequest));
    }

    //일정 삭제
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
