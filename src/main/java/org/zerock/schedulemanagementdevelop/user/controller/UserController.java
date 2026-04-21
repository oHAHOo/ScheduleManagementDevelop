package org.zerock.schedulemanagementdevelop.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedulemanagementdevelop.exception.UnauthorizedException;
import org.zerock.schedulemanagementdevelop.user.dto.*;
import org.zerock.schedulemanagementdevelop.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signup(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserRequest));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession httpSession) {

        //세션에 유저 정보 저장
        SessionUser sessionUser = userService.login(loginRequest);
        httpSession.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //유저 단일 조회
    @GetMapping("/users/{id}")
    public ResponseEntity<GetUserResponse> getOneUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    //유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    //유저 정보 수정
    @PutMapping("/users/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest,
            HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, updateUserRequest, sessionUser.getId()));
    }

    //유저 삭제
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");
        if (sessionUser == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        userService.deleteById(id, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
