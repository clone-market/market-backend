package com.market.payment.entity;

import com.market.common.domain.BaseTimeEntity;
import com.market.order.entity.Order;
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
@EqualsAndHashCode(of = "id",callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private int pointAmount;

    @NotNull
    private int cashAmount;

    @NotNull
    @Enumerated(STRING)
    private PaymentStatus status;

    @NotNull
    @OneToOne(fetch = LAZY)
    private Order order;
}
