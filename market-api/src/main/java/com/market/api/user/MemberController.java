package com.market.api.user;

import com.market.api.user.dto.findInfo.ChangePwdParam;
import com.market.api.user.dto.findInfo.FindIdParam;
import com.market.api.user.dto.findInfo.FindPwdParam;
import com.market.api.user.dto.signUp.AuthCodeParam;
import com.market.api.user.dto.signUp.DuplicationCheckParam;
import com.market.api.user.dto.signUp.MemberEmailParam;
import com.market.api.user.dto.signUp.MemberSignUpParam;
import com.market.api.user.validator.MemberSignUpValidator;
import com.market.member.entity.Member;
import com.market.member.repository.MemberRepository;
import com.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO: 2021-06-18[양동혁] corssOrigin
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
        return ResponseEntity.ok("가입완료");
    }

    @GetMapping("/mailAuth")
    public ResponseEntity<String> issueAuthCode(@Valid @RequestBody MemberEmailParam memberEmailParam) {
        memberService.generateAuthCode(memberEmailParam.getEmail());
        return ResponseEntity.ok("인증코드 발급성공");
    }

    @PostMapping({"/mailAuth"})
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

    @GetMapping("/findId")
    public ResponseEntity<Map<String, String>> findId(@Valid @RequestBody FindIdParam findIdParam)  {
        Optional<Member> optionalMember = memberRepository.findByNameAndEmail(findIdParam.getName(), findIdParam.getEmail());

        Map<String, String> resMap = new HashMap<>();
        if (optionalMember.isEmpty()) {
            resMap.put("message", "해당하는 계정이 없습니다.");
            return ResponseEntity.badRequest().body(resMap);
        }

        String username = optionalMember.get().getUsername();
        String resUsername = username.substring(0, username.length() - 3)
                .concat("***");
        resMap.put("username", resUsername);
        return ResponseEntity.ok(resMap);
    }

    @GetMapping("/findPwd")
    public ResponseEntity<Map<String, String>> findPassword(@Valid @RequestBody FindPwdParam findPwdParam) {
        Optional<Member> optionalMember = memberRepository.findByNameAndEmailAndUsername(findPwdParam.getName(),
                findPwdParam.getEmail(), findPwdParam.getUsername());

        Map<String, String> resMap = new HashMap<>();
        if (optionalMember.isEmpty()) {
            resMap.put("message", "해당하는 계정이 없습니다.");
            return ResponseEntity.badRequest().body(resMap);
        }
        return ResponseEntity.ok(resMap);
    }

    @PostMapping("/changePwd")
    public ResponseEntity<?> findPasswordAuth(@Valid @RequestBody ChangePwdParam changePwdParam) {
        if (!changePwdParam.getPassword().equals(changePwdParam.getPasswordChk())) {
            return ResponseEntity.badRequest().body("비밀번호확인이 일치하지 않습니다.");
        }
        memberService.changePassword(changePwdParam.getEmail(), changePwdParam.getPassword());
        return ResponseEntity.ok().build();
    }
}