package com.market.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PAID("결제완료"),
    CANCELED("결제취소");

    private final String title;
}
