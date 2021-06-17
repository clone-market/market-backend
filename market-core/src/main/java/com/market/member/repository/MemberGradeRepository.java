package com.market.member.repository;

import com.market.member.entity.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Long> {

    Optional<MemberGrade> findByName(String name);
}
