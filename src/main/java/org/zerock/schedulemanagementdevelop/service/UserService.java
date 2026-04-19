package org.zerock.schedulemanagementdevelop.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.schedulemanagementdevelop.config.DuplicateEmailException;
import org.zerock.schedulemanagementdevelop.config.InvalidPasswordException;
import org.zerock.schedulemanagementdevelop.config.AccessDeniedException;
import org.zerock.schedulemanagementdevelop.config.UserNotFoundException;
import org.zerock.schedulemanagementdevelop.dto.UserDto.*;
import org.zerock.schedulemanagementdevelop.entity.User;
import org.zerock.schedulemanagementdevelop.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public CreateUserResponse saveUser(CreateUserRequest createUserRequest) {
        User user = new User(createUserRequest.getUsername(), createUserRequest.getEmail(), createUserRequest.getPassword());
        if(userRepository.existsByEmail(createUserRequest.getEmail())) {
            throw new DuplicateEmailException("이미 존재하는 이메일입니다.");
        }
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public GetUserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        return new GetUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getCreatedAt(), user.getModifiedAt());
    }

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

    @Transactional
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest, Long userId) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        if(!user.getId().equals(userId)){
            throw new AccessDeniedException("본인만 수정할 수 있습니다.");
        }
        user.updateUser(updateUserRequest.getUsername(), updateUserRequest.getEmail());
        return  new UpdateUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getModifiedAt());
    }

    @Transactional
    public void deleteById(Long id, Long userId) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getId().equals(userId)) {
            throw new AccessDeniedException("본인만 삭제할 수 있습니다.");
        }

        userRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()-> new UserNotFoundException("User not found"));

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw new InvalidPasswordException("Wrong password");
        }
        return new SessionUser(user.getId(),user.getUsername());
    }
}
