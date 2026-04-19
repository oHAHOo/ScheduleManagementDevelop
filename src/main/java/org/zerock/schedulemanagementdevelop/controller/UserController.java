package org.zerock.schedulemanagementdevelop.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.schedulemanagementdevelop.config.UnauthorizedException;
import org.zerock.schedulemanagementdevelop.service.UserService;
import org.zerock.schedulemanagementdevelop.dto.UserDto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponse> signup(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(createUserRequest));
    }

    //로그인 (분리해야할수도)
    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        SessionUser sessionUser= userService.login(loginRequest);
        httpSession.setAttribute("loginUser", sessionUser);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<GetUserResponse> getOneUser(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest updateUserRequest,
            HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");

        if(sessionUser==null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, updateUserRequest, sessionUser.getId()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("loginUser");
        if(sessionUser==null){
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        userService.deleteById(id, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
