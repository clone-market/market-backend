package com.market.api.user.validator;

import com.market.api.user.dto.MemberSignUpParam;
import com.market.member.repository.MemberRepository;
import com.market.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberSignUpValidator implements Validator {

    private final MemberRepository memberRepository;

    private final MemberService memberService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberSignUpParam.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberSignUpParam memberSignUpParam = (MemberSignUpParam) target;
        if (memberRepository.findByUsername(memberSignUpParam.getUsername()).isPresent()) {
            errors.rejectValue("username", "invalid.username", "이미 사용중인 아이디입니다.");
        }

        if (memberRepository.findByEmail(memberSignUpParam.getEmail()).isPresent()) {
            errors.rejectValue("email", "invalid.email", "이미 사용중인 이메일입니다.");
        }

        if (!memberSignUpParam.getPassword().equals(memberSignUpParam.getPasswordChk())) {
            errors.rejectValue("password", "invalid.password", "비밀번호가 일치하지 않습니다.");
        }

        if (!memberService.isValidatedEmail(memberSignUpParam.getEmail())) {
            errors.rejectValue("email", "invalid.email", "이메일인증을 하지 않았거나 시간이 만료되었습니다.");
        }
    }
}
