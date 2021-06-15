package com.market.order.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    MEMBER("회원"),
    GUEST("비회원");

    private final String title;
}
