package com.market.member.entity;

import com.market.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberGrade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String name;

    //단위: %
    @NotNull
    private double accrualRate;

    public MemberGrade(@NotNull String name, @NotNull double accrualRate) {
        this.name = name;
        this.accrualRate = accrualRate;
    }
}
