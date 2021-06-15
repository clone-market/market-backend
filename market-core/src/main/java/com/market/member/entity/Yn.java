package com.market.member.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Yn {
    Y("수락"),
    N("거부");

    private final String title;
}