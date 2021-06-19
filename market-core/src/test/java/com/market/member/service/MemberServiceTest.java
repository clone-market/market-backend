package com.market.member.service;

import com.market.mail.dto.EmailMessageParam;
import com.market.mail.service.EmailService;
import com.market.member.dto.AddressCreationParam;
import com.market.member.dto.MemberCreationParam;
import com.market.member.dto.TermsCreationParam;
import com.market.member.entity.*;
import com.market.member.repository.AddressRepository;
import com.market.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AddressRepository addressRepository;

    @MockBean
    EmailService emailService;

    private final String EMAIL = "user@email.com";

    private static final Grade NORMAL_GRADE = Grade.FREE;

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        //given
        MemberCreationParam memberCreationParam = createMemberParam();
        String authCode = memberService.generateAuthCode(EMAIL);
        memberService.validateAuthCode(EMAIL, authCode);

        //when
        Long memberId = memberService.signUp(memberCreationParam);
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        //then
        assertThat(optionalMember).isNotEmpty();
        Member member = optionalMember.get();

        List<Address> addresses = addressRepository.findAllByMemberId(memberId);
        assertThat(addresses.size()).isEqualTo(1);
        assertThat(addresses.get(0).getMember()).isEqualTo(member);
        assertThat(member.getGrade()).isEqualTo(NORMAL_GRADE);
        assertThat(member.getPoint().getCurrentPoint()).isEqualTo(0);
        assertThat(member.getTerms()).isNotNull();
        BDDMockito.then(emailService).should().send(any(EmailMessageParam.class));
    }

    @Test
    @DisplayName("인증코드 검증 성공")
    void validateAuthCode() throws Exception {
        //given
        String authToken = memberService.generateAuthCode(EMAIL);

        //when
        boolean isValidated = memberService.validateAuthCode(EMAIL, authToken);

        //then
        assertThat(isValidated).isTrue();
    }

    @Test
    @DisplayName("인증코드 검증 실패 - 잘못된 인증코드")
    void validateAuthCodeWithInvalidAuthCode() throws Exception {
        //given
        String authCode1 = memberService.generateAuthCode(EMAIL);
        String authCode2 = memberService.generateAuthCode(EMAIL); //authCode1은 더이상 유효하지 않음
        //

        //when
        boolean isValidated = memberService.validateAuthCode(EMAIL, authCode1);

        //then
        assertThat(isValidated).isFalse();
    }

    public MemberCreationParam createMemberParam() {
        return MemberCreationParam.builder()
                .username("아이디")
                .password("패스워드")
                .email(EMAIL)
                .name("양동혁")
                .gender(Gender.M)
                .birthday(LocalDateTime.now())
                .phoneNumber("010-1234-1234")
                .termsCreationParam(createTermsParam())
                .addressCreationParam(createAddressParam())
                .build();
    }

    private AddressCreationParam createAddressParam() {
        return AddressCreationParam.builder()
                .zipCode("123-123")
                .streetNameAddress("도로명주소")
                .lotNumberAddress("지번주소")
                .detailedAddress("101동 101호")
                .build();
    }

    private TermsCreationParam createTermsParam() {
        return TermsCreationParam.builder()
                .agreeYn(Yn.Y)
                .consentReqYn(Yn.Y)
                .consentOptYn(Yn.Y)
                .mailYn(Yn.Y)
                .smsYn(Yn.Y)
                .fourteenYn(Yn.Y)
                .build();
    }

}