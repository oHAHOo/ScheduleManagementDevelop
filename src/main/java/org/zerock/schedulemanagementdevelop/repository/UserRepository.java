package org.zerock.schedulemanagementdevelop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zerock.schedulemanagementdevelop.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
