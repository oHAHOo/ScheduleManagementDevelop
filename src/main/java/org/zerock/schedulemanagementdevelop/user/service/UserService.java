package org.zerock.schedulemanagementdevelop.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.config.PasswordEncoder;
import org.zerock.schedulemanagementdevelop.exception.AccessDeniedException;
import org.zerock.schedulemanagementdevelop.exception.DuplicateEmailException;
import org.zerock.schedulemanagementdevelop.exception.InvalidPasswordException;
import org.zerock.schedulemanagementdevelop.exception.UserNotFoundException;
import org.zerock.schedulemanagementdevelop.user.dto.*;
import org.zerock.schedulemanagementdevelop.user.entity.User;
import org.zerock.schedulemanagementdevelop.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest createUserRequest) {

        //이메일 중복확인
        if (userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        //비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        User user = new User(createUserRequest.getUsername(), createUserRequest.getEmail(), encodedPassword);

        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }

    //사용자 단일 조회
    @Transactional(readOnly = true)
    public GetUserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        return new GetUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt());
    }

    //사용자 전체 조회
    @Transactional(readOnly = true)
    public List<GetUserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetUserResponse dto = new GetUserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getCreatedAt(),
                    user.getModifiedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //사용자 정보 수정
    @Transactional
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest, Long userId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        //권한 확인
        if (!user.getId().equals(userId)) {
            throw new AccessDeniedException("본인만 수정할 수 있습니다.");
        }
        user.updateUser(updateUserRequest.getUsername(), updateUserRequest.getEmail());
        return new UpdateUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getModifiedAt());
    }

    //사용자 정보 삭제
    @Transactional
    public void deleteById(Long id, Long userId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        //권한 확인
        if (!user.getId().equals(userId)) {
            throw new AccessDeniedException("본인만 삭제할 수 있습니다.");
        }

        userRepository.deleteById(id);
    }

    //로그인
    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new UserNotFoundException("사용자를 찾을 수 없습니다."));

        //비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다");
        }
        return new SessionUser(user.getId(), user.getUsername());
    }
}
