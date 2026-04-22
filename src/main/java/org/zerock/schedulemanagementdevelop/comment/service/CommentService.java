package org.zerock.schedulemanagementdevelop.comment.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.comment.dto.CreateCommentRequest;
import org.zerock.schedulemanagementdevelop.comment.dto.CreateCommentResponse;
import org.zerock.schedulemanagementdevelop.comment.dto.GetCommentResponse;
import org.zerock.schedulemanagementdevelop.comment.entity.Comment;
import org.zerock.schedulemanagementdevelop.comment.repository.CommentRepository;
import org.zerock.schedulemanagementdevelop.config.SessionUtils;
import org.zerock.schedulemanagementdevelop.exception.ScheduleNotFoundException;
import org.zerock.schedulemanagementdevelop.exception.UserNotFoundException;
import org.zerock.schedulemanagementdevelop.schedule.entity.Schedule;
import org.zerock.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import org.zerock.schedulemanagementdevelop.user.dto.SessionUser;
import org.zerock.schedulemanagementdevelop.user.entity.User;
import org.zerock.schedulemanagementdevelop.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //댓글 생성
    @Transactional
    public CreateCommentResponse createComment(Long scheduleId, HttpSession httpSession, CreateCommentRequest createCommentRequest) {
        //일정 조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new ScheduleNotFoundException("일정을 찾을 수 없습니다."));

        //로그인 유저 확인
        SessionUser sessionUser = SessionUtils.getLoginUser(httpSession);

        //유저 조회
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        //댓글 저장
        Comment comment = new Comment(createCommentRequest.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(savedComment.getId(), sessionUser.getId(), schedule.getId(), savedComment.getContent(), savedComment.getCreatedAt(), savedComment.getModifiedAt());
    }

    //특정 일정의 댓글 전체 조회
    @Transactional(readOnly = true)
    public List<GetCommentResponse> findAll(Long scheduleId) {
        //특정 일정의 댓글 목록 조회
        return commentRepository.findBySchedule_Id(scheduleId).stream()
                .map(comment -> new GetCommentResponse(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getSchedule().getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()
                )).collect(Collectors.toList());
    }
}
