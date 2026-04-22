package org.zerock.schedulemanagementdevelop.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.zerock.schedulemanagementdevelop.common.BaseEntity;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //사용자 이름
    @Column(nullable = false)
    private String username;

    //사용자 이메일
    @Column(nullable = false, unique = true)
    private String email;

    //비밀번호
    @Column(nullable = false)
    private String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUser(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
