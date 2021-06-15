package com.market.member.repository;

import com.market.member.entity.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long> {
}
