package org.zerock.schedulemanagementdevelop.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.schedulemanagementdevelop.comment.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 일정(scheduleId)에 달린 댓글 목록 조회
    List<Comment> findBySchedule_Id(Long scheduleId);
}
