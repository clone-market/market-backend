package com.market.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.mail.dto.EmailMessageParam;
import com.market.mail.service.EmailService;
import com.market.member.entity.Address;
import com.market.member.entity.Grade;
import com.market.member.entity.Member;
import com.market.member.repository.AddressRepository;
import com.market.member.repository.MemberRepository;
import com.market.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AddressRepository addressRepository;

    @MockBean
    EmailService emailService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private final String EMAIL = "user@email.com";

    @Test
    @DisplayName("회원가입")
    void signUp() throws Exception {
        //given
        String memberSignUpParam = createMemberSignUpParam("M");

        //when
        /* 이메일 인증 */
        String authCode = memberService.generateAuthCode(EMAIL);
        memberService.validateAuthCode(EMAIL, authCode);

        /* 회원 가입 */
        mockMvc.perform(post("/api/v2/user/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(memberSignUpParam))
                .andExpect(status().isOk());

        //then
        Optional<Member> optionalMember = memberRepository.findByEmail(EMAIL);
        assertThat(optionalMember).isNotEmpty();

        Member member = optionalMember.get();
        List<Address> addresses = addressRepository.findAllByMemberId(member.getId());

        assertThat(addresses.size()).isEqualTo(1);
        assertThat(addresses.get(0).getMember()).isEqualTo(member);
        assertThat(member.getGrade()).isEqualTo(Grade.FREE);
        assertThat(member.getPoint().getCurrentPoint()).isEqualTo(0);
        assertThat(member.getTerms()).isNotNull();
        then(emailService).should().send(any(EmailMessageParam.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 입력형식오류")
    void signUpWithIllegalArgument() throws Exception {
        //given
        String memberSignUpParam = createMemberSignUpParam("QWE");

        //when
        /* 이메일 인증 */
        String authCode = memberService.generateAuthCode(EMAIL);
        memberService.validateAuthCode(EMAIL, authCode);

        /* 회원 가입 */
        mockMvc.perform(post("/api/v2/user/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(memberSignUpParam))
                .andExpect(status().isBadRequest());

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    private String createMemberSignUpParam(String gender) throws Exception {
        Map<String, String> memberSignupParam = new HashMap<>();
        memberSignupParam.put("username", "qwer2");
        memberSignupParam.put("password", "pwd");
        memberSignupParam.put("passwordChk", "pwd");
        memberSignupParam.put("name", "name");
        memberSignupParam.put("email", EMAIL);
        memberSignupParam.put("phoneNumber", "010-1234-5678");
        memberSignupParam.put("zipCode", "1234-1234");
        memberSignupParam.put("streetNameAddress", "행신로");
        memberSignupParam.put("lotNumberAddress", "행-신-로");
        memberSignupParam.put("detailedAddress", "101호");
        memberSignupParam.put("birthday", "1996-11-16T00:00:00");
        memberSignupParam.put("gender", gender);
        memberSignupParam.put("agreeYn", "Y");
        memberSignupParam.put("consentReqYn", "Y");
        memberSignupParam.put("consentOptYn", "Y");
        memberSignupParam.put("fourteenYn", "Y");

        return objectMapper.writeValueAsString(memberSignupParam);
    }

}