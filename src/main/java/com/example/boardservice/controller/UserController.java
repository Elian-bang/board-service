package com.example.boardservice.controller;

import com.example.boardservice.aop.LoginCheck;
import com.example.boardservice.dto.UserDTO;
import com.example.boardservice.dto.UserDTO.Status;
import com.example.boardservice.dto.request.UserDeleteId;
import com.example.boardservice.dto.request.UserLoginRequest;
import com.example.boardservice.dto.request.UserUpdatePasswordRequest;
import com.example.boardservice.dto.response.LoginResponse;
import com.example.boardservice.dto.response.UserInfoResponse;
import com.example.boardservice.service.impl.UserServiceImpl;
import com.example.boardservice.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserServiceImpl userService;
    private static LoginResponse loginResponse;

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullDataBeforeSignup(userDTO)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDTO);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest userLoginRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String userId = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        UserDTO user = userService.login(userId, password);

        if (user == null) {
            return HttpStatus.NOT_FOUND;
        } else if (user != null) {
            loginResponse = LoginResponse.success(user);
            if (user.getStatus() == (Status.ADMIN)) {
                SessionUtil.setLoginAdminId(session, user.getUserId());
            } else {
                SessionUtil.setLoginMemberId(session, user.getUserId());
            }

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지원되지 않는 유저입니다.");
        }

        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse getMyInfo(HttpSession session) {
        String userId = SessionUtil.getLoginMemberId(session);
        if (userId == null) userId = SessionUtil.getLoginAdminId(session);
        UserDTO member = userService.getUserInfo(userId);
        return new UserInfoResponse(member);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @LoginCheck(type = LoginCheck.UserType.USER)
    @PatchMapping("password")
    public ResponseEntity<LoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = accountId;
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(id, beforePassword, afterPassword);
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패 : {}", e.getMessage());
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId, HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("deleteUserProfile 실패 : {}", e.getMessage());
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
