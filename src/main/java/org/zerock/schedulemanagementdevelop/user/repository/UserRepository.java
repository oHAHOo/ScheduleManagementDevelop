package org.zerock.schedulemanagementdevelop.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.schedulemanagementdevelop.user.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //이메일로 유저 조회
    Optional<User> findByEmail(String email);

    //이메일 중복 확인
    boolean existsByEmail(String email);
}
