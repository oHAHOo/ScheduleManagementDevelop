package org.zerock.schedulemanagementdevelop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalStateException("User not found"));
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
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalStateException("User not found"));
        user.updateUser(updateUserRequest.getUsername(), updateUserRequest.getEmail());
        return  new UpdateUserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getModifiedAt());
    }

    @Transactional
    public void deleteById(Long id) {
        boolean existence = userRepository.existsById(id);
        if (!existence) {
            throw new IllegalStateException("User not found");
        }
        userRepository.deleteById(id);
    }


}
