package com.market.order.entity;

import com.market.cart.entiy.Cart;
import com.market.common.domain.BaseTimeEntity;
import com.market.member.entity.Address;
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

@Entity(name = "orders")
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(STRING)
    private OrderType Type;

    @NotNull
    @Enumerated(STRING)
    private OrderStatus status;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Cart cart;

    @NotNull
    @OneToOne(fetch = LAZY)
    private Address address;

    @ManyToOne(fetch = LAZY)
    private Member member;
}
