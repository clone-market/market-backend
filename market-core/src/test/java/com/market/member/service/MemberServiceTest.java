package com.market.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    private final String EMAIL = "user@email.com";

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

}