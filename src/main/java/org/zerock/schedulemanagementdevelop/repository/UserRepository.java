package org.zerock.schedulemanagementdevelop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.schedulemanagementdevelop.entity.Schedule;
import org.zerock.schedulemanagementdevelop.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String userName);
}
