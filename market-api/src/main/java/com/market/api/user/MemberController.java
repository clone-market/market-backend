package com.market.api.user;

import com.market.api.user.dto.findInfo.ChangePwdParam;
import com.market.api.user.dto.findInfo.FindIdParam;
import com.market.api.user.dto.findInfo.FindPwdParam;
import com.market.api.user.dto.signUp.AuthCodeParam;
import com.market.api.user.dto.signUp.DuplicationCheckParam;
import com.market.api.user.dto.signUp.MemberEmailParam;
import com.market.api.user.dto.signUp.MemberSignUpParam;
import com.market.api.user.exceptionHandler.ErrorParam;
import com.market.api.user.validator.MemberSignUpValidator;
import com.market.member.entity.Member;
import com.market.member.repository.MemberRepository;
import com.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
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
    public ResponseEntity<Void> SignUp(@Valid @RequestBody MemberSignUpParam memberSignUpParam) {
        memberService.signUp(memberSignUpParam.toServiceDto());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mailAuth")
    public ResponseEntity<Void> issueAuthCode(@Valid @RequestBody MemberEmailParam memberEmailParam) {
        memberService.generateAuthCode(memberEmailParam.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping({"/mailAuth"})
    public ResponseEntity<?> validateAuthCode(@Valid @RequestBody AuthCodeParam authCodeParam) {
        // TODO: 2021-06-16[양동혁] JWT에서 이메일 가져오도록 변경
        if (memberService.validateAuthCode(authCodeParam.getEmail(), authCodeParam.getAuthCode())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body(new ErrorParam("인증실패"));
    }

    @GetMapping("/signUp/chk")
    public ResponseEntity<?> checkDuplication(@Valid @RequestBody DuplicationCheckParam duplicationCheckParam) {
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
            return ResponseEntity.badRequest().body(new ErrorParam("사용불가"));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findId")
    public ResponseEntity<?> findId(@Valid @RequestBody FindIdParam findIdParam)  {
        Optional<Member> optionalMember = memberRepository.findByNameAndEmail(findIdParam.getName(), findIdParam.getEmail());

        if (optionalMember.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorParam("해당하는 계정이 없습니다."));
        }


        String username = optionalMember.get().getUsername();
        String resUsername = username.substring(0, username.length() - 3)
                .concat("***");
        Map<String, String> resMap = new HashMap<>() {{
            put("username", resUsername);
        }};
        return ResponseEntity.ok(resMap);
    }

    @GetMapping("/findPwd")
    public ResponseEntity<?> findPassword(@Valid @RequestBody FindPwdParam findPwdParam) {
        Optional<Member> optionalMember = memberRepository.findByNameAndEmailAndUsername(findPwdParam.getName(),
                findPwdParam.getEmail(), findPwdParam.getUsername());
        if (optionalMember.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorParam("해당하는 계정이 없습니다."));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/changePwd")
    public ResponseEntity<?> findPasswordAuth(@Valid @RequestBody ChangePwdParam changePwdParam) {
        if (!changePwdParam.getPassword().equals(changePwdParam.getPasswordChk())) {
            return ResponseEntity.badRequest().body(new ErrorParam("비밀번호확인이 일치하지 않습니다."));
        }
        memberService.changePassword(changePwdParam.getEmail(), changePwdParam.getPassword());
        return ResponseEntity.ok().build();
    }
}