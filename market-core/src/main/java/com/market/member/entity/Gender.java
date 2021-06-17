package com.market.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    M("남성"),
    F("여성"),
    N("없음");

    private final String title;
}
