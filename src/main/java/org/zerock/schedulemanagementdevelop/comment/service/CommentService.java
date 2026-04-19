package org.zerock.schedulemanagementdevelop.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.comment.dto.CreateCommentRequest;
import org.zerock.schedulemanagementdevelop.comment.dto.CreateCommentResponse;
import org.zerock.schedulemanagementdevelop.comment.dto.GetCommentResponse;
import org.zerock.schedulemanagementdevelop.comment.entity.Comment;
import org.zerock.schedulemanagementdevelop.comment.repository.CommentRepository;
import org.zerock.schedulemanagementdevelop.exception.SchduleNotFoundException;
import org.zerock.schedulemanagementdevelop.schedule.entity.Schedule;
import org.zerock.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import org.zerock.schedulemanagementdevelop.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse createComment(Long scheduleId,CreateCommentRequest createCommentRequest) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new SchduleNotFoundException("일정을 찾을 수 없습니다."));
        User user = schedule.getUser();
        Comment comment = new  Comment(createCommentRequest.getContent(), user, schedule);
        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getCommentId(),
                user.getId(),
                schedule.getId(),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetCommentResponse> findAll(Long scheduleId) {
         List<Comment> comments = commentRepository.findByAllScheduleId(scheduleId);
         List<GetCommentResponse> dtos = new ArrayList<>();
         for (Comment comment : comments) {
             GetCommentResponse dto = new GetCommentResponse(
                     comment.getCommentId(),
                     comment.getUser().getId(),
                     comment.getSchedule().getId(),
                     comment.getContent(),
                     comment.getCreatedAt(),
                     comment.getModifiedAt()
             );
             dtos.add(dto);
         }
         return dtos;
    }
}
