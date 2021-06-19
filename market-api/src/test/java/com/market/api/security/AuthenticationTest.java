package com.market.api.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.api.security.model.LoginRequest;
import com.market.api.security.util.JwtUtil;
import com.market.member.entity.*;
import com.market.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private JwtUtil jwtUtil;

    public static final String USERNAME = "testMan";
    public static final String PASSWORD = "test";

    @BeforeEach
    void createMember() {
        memberRepository.save(Member.builder()
                .username(USERNAME)
                .password(passwordEncoder.encode(PASSWORD))
                .email("test@email.com")
                .gender(Gender.M)
                .name("마켓애용가")
                .phoneNumber("010-1234-5678")
                .terms(MemberTerms.builder()
                    .agreeYn(Yn.Y)
                    .consentOptYn(Yn.Y)
                    .consentReqYn(Yn.Y)
                    .fourteenYn(Yn.Y)
                    .mailYn(Yn.Y)
                    .smsYn(Yn.Y)
                    .build()
                )
                .grade(Grade.FREE)
                .birthDay(LocalDateTime.now())
                .build());
    }
    @Test
    @DisplayName("로그인 테스트")
    void signInTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v2/user/signIn")
                .content(getLoginInfo()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String jwtToken = result.getResponse().getContentAsString().replaceAll("\"", "");

        Claims body = jwtUtil.parserToken(jwtToken).getBody();
        assertThat(body.getSubject()).isEqualTo("testMan");
        assertThat(body.getIssuer()).isEqualTo("clone-market");
    }
    private String getLoginInfo() throws JsonProcessingException {
        return objectMapper.writeValueAsString(LoginRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build());
    }
}