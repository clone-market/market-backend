package com.market.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 조회 테스트")
    public void findMemberTest() {
        memberRepository.save(Member.builder()
                .name("gunkim")
                .phoneNumber("010-1234-5678")
                .birthDay(LocalDateTime.now())
                .email("gunkim.dev@gmail.com")
                .gender(Gender.M)
                .password("test")
                .SmsYn(Yn.Y)
                .WebYn(Yn.Y)
                .username("말파이트")
                .build());

        Member member = memberRepository.findAll().get(0);
        assertThat(member.getName(), is(equalTo("gunkim")));
        assertThat(member.getEmail(), is(equalTo("gunkim.dev@gmail.com")));
    }
}
