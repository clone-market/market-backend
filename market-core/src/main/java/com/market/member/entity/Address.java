package com.market.member.entity;

import com.market.common.domain.BaseTimeEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

// TODO: 2021-06-11  비회원이 생성한 배송정보는 7일보관후 삭제
@Entity
@EqualsAndHashCode(of = "id", callSuper = true)
@Getter @NoArgsConstructor(access = PROTECTED)
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String zipCode;

    @NotNull
    private String streetNameAddress;

    @NotNull
    private String lotNumberAddress;

    @NotNull
    private String detailedAddress;

    @NotNull
    private boolean isBasicAddress;

    @ManyToOne(fetch = LAZY)
    private Member member;
}
