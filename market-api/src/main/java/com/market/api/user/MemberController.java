package com.market.api.user;

import com.market.api.user.dto.AuthCodeParam;
import com.market.api.user.dto.DuplicationCheckParam;
import com.market.api.user.dto.MemberEmailParam;
import com.market.api.user.dto.MemberSignUpParam;
import com.market.api.user.validator.MemberSignUpValidator;
import com.market.member.repository.MemberRepository;
import com.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v2/user")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    private final MemberSignUpValidator memberSignUpValidator;

    @InitBinder(value = "memberSignUpParam")
    public void MemberParamInitBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberSignUpValidator);
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> SignUp(@Valid @RequestBody MemberSignUpParam memberSignUpParam) {
        memberService.signUp(memberSignUpParam.toServiceDto());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/signUp/auth")
    public ResponseEntity<String> issueAuthCode(@Valid @RequestBody MemberEmailParam memberEmailParam) {
        memberService.generateAuthCode(memberEmailParam.getEmail());
        return ResponseEntity.ok("인증코드 발급성공");
    }

    @PostMapping("/signUp/auth")
    public ResponseEntity<String> validateAuthCode(@Valid @RequestBody AuthCodeParam authCodeParam) {
        // TODO: 2021-06-16[양동혁] JWT에서 이메일 가져오도록 변경
        if (memberService.validateAuthCode(authCodeParam.getEmail(), authCodeParam.getAuthCode())) {
            return ResponseEntity.ok("인증성공");
        }
        return ResponseEntity.badRequest().body("인증실패");
    }

    @GetMapping("/signUp/chk")
    public ResponseEntity<String> checkDuplication(@Valid @RequestBody DuplicationCheckParam duplicationCheckParam) {
        boolean isExist = false;
        switch (duplicationCheckParam.getMode()) {
            case EMAIL:
                isExist = memberRepository.findByEmail(duplicationCheckParam.getEmail()).isPresent();
                break;
            case USERNAME:
                isExist = memberRepository.findByUsername(duplicationCheckParam.getUsername()).isPresent();
                break;
        }

        if (isExist) {
            return ResponseEntity.badRequest().body("사용불가");
        }
        return ResponseEntity.ok("사용가능");
    }
}