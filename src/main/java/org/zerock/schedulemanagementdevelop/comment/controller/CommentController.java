package org.zerock.schedulemanagementdevelop.comment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedulemanagementdevelop.comment.dto.CreateCommentRequest;
import org.zerock.schedulemanagementdevelop.comment.dto.CreateCommentResponse;
import org.zerock.schedulemanagementdevelop.comment.dto.GetCommentResponse;
import org.zerock.schedulemanagementdevelop.comment.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}")
public class CommentController {
    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CreateCommentResponse> createComment(@PathVariable Long scheduleId, HttpSession httpSession, @Valid @RequestBody CreateCommentRequest createCommentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(scheduleId, httpSession, createCommentRequest));
    }

    //댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<GetCommentResponse>> getAllComment(@PathVariable Long scheduleId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.findAll(scheduleId));
    }

}
