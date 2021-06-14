package com.market.product.entity;

import com.market.member.entity.Member;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class ProductBoard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private int helpCount = 0;

    @NotNull
    private int views = 0;

    @NotNull
    @Lob @Basic(fetch = LAZY)
    private String description;

    @NotNull
    @Enumerated(STRING)
    private ProductBoardType type;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Member member;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Product product;
}
