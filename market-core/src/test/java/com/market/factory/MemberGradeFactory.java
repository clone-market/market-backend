package com.market.factory;

import com.market.member.entity.MemberGrade;
import com.market.member.repository.MemberGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberGradeFactory {

    @Autowired
    MemberGradeRepository memberGradeRepository;

    private static final String GRADE_NAME = "기본";
    private static final double GRADE_ACCRUAL_RATE = 1;

    public MemberGrade createMemberGrade() {
        return memberGradeRepository.save(new MemberGrade(GRADE_NAME, GRADE_ACCRUAL_RATE));
    }
}
