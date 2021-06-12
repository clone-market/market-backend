package com.market.member.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Yn {
    Y("수락"),
    N("거부");

    private String title;
}