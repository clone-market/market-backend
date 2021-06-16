package com.market.api.user;

import com.market.member.service.UserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // TODO: 2021-06-16[양동혁] 예외 핸들링
    @GetMapping("/signUp/auth")
    public ResponseEntity<String> issueAuthCode(@Valid @RequestBody MemberEmail memberEmail) {
        // TODO: 2021-06-17[양동혁] 이메일 중복 검증로직 추가
        userService.generateAuthCode(memberEmail.getEmail());
        return new ResponseEntity<>("인종코드 발급성공", HttpStatus.OK);
    }


    @PostMapping("/signUp/auth")
    public ResponseEntity<String> validateAuthCode(@Valid @RequestBody AuthCode authCode) {
        // TODO: 2021-06-16[양동혁] JWT에서 이메일 가져오도록 변경
        if (userService.validateAuthCode(authCode.getEmail(), authCode.getAuthCode())) {
            return new ResponseEntity<>("인증성공", HttpStatus.OK);
        }
        return new ResponseEntity<>("인증실패", HttpStatus.BAD_REQUEST);
    }

    @Getter
    @NoArgsConstructor
    private static class MemberEmail {
        @Email
        @NotNull
        private String email;
    }

    @Getter
    @NoArgsConstructor
    private static class AuthCode {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String authCode;
    }
}