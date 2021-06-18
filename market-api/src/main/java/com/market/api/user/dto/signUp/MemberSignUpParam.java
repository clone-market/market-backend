package com.market.api.user.dto.signUp;

import com.market.member.dto.AddressCreationParam;
import com.market.member.dto.MemberCreationParam;
import com.market.member.dto.TermsCreationParam;
import com.market.member.entity.Gender;
import com.market.member.entity.Yn;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class MemberSignUpParam {
    
    @NotNull(message = "아이디를 입력하세요.")
    private String username;
    
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
    
    @NotBlank(message = "비밀번호를 입력하세요")
    private String passwordChk;

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입려하세요.")
    private String email;

    @NotBlank(message = "휴대폰번호를 입력하세요.")
    private String phoneNumber;

    @NotBlank(message = "우편번호를 입력하세요")
    private String zipCode;

    @NotBlank(message = "도로명 주소를 입력하세요.")
    private String streetNameAddress;

    @NotBlank(message = "지번 주소를 입력하세요.")
    private String lotNumberAddress;

    @NotBlank(message = "상세 주소를 입력하세요.")
    private String detailedAddress;

    // TODO: 2021-06-17[양동혁] 잘못된 값 왔을 때 에러 어떻게옴? 값 없을때 json 처리
    @NotNull
    private Gender gender = Gender.N;

    @NotNull
    private LocalDateTime birthday = LocalDateTime.of(1900, 1, 1, 0 ,0);
    
    @NotNull(message = "미동의시 회원가입 할 수 없습니다")
    private Yn agreeYn;

    @NotNull(message = "미동의시 회원가입 할 수 없습니다")
    private Yn consentReqYn;

    @NotNull
    private Yn consentOptYn = Yn.N;

    @NotNull
    private Yn smsYn = Yn.N;

    @NotNull
    private Yn mailYn = Yn.N;
    
    @NotNull(message = "미동의 회원가입 할 수 없습니다")
    private Yn fourteenYn;

    public MemberCreationParam toServiceDto() {
        return MemberCreationParam.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .gender(gender)
                .birthday(birthday)
                .phoneNumber(phoneNumber)
                .termsCreationParam(createTermsParam())
                .addressCreationParam(createAddressParam())
                .build();
    }

    private AddressCreationParam createAddressParam() {
        return AddressCreationParam.builder()
                .zipCode(zipCode)
                .streetNameAddress(streetNameAddress)
                .lotNumberAddress(lotNumberAddress)
                .detailedAddress(detailedAddress)
                .build();
    }

    private TermsCreationParam createTermsParam() {
        return TermsCreationParam.builder()
                .agreeYn(agreeYn)
                .consentReqYn(consentReqYn)
                .consentOptYn(consentOptYn)
                .mailYn(mailYn)
                .smsYn(smsYn)
                .fourteenYn(fourteenYn)
                .build();
    }
}
