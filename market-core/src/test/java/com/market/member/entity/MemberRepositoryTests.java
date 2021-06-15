package com.market.member.entity;

import com.market.factory.MemberGradeFactory;
import com.market.member.dto.MemberParam;
import com.market.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberGradeFactory memberGradeFactory;

    @Test
    @DisplayName("회원 조회 테스트")
    public void findMemberTest() {
        Long memberId = createMember();

        Member member = memberRepository.findById(memberId).get();
        assertThat(member.getName(), is(equalTo("gunkim")));
        assertThat(member.getEmail(), is(equalTo("gunkim.dev@gmail.com")));
        assertThat(member.getGrade(), notNullValue());
        assertThat(member.getPoint().getAccumulatedPoint(), is(equalTo(0)));
    }

    private Long createMember() {
        return memberRepository
                .save(Member.createMember(createMemberParam()))
                .getId();
    }

    private MemberParam createMemberParam() {
        return MemberParam.builder()
                .name("gunkim")
                .phoneNumber("010-1234-5678")
                .birthDay(LocalDateTime.now())
                .email("gunkim.dev@gmail.com")
                .gender(Gender.M)
                .password("test")
                .SmsYn(Yn.Y)
                .WebYn(Yn.Y)
                .username("말파이트")
                .memberGrade(memberGradeFactory.createMemberGrade())
                .build();
    }
}
