package com.market.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
    A("A등급", 20),
    B("B등급", 15),
    C("C등급", 10),
    FREE("일반 회원", 5),
    ADMIN("관리자", 0);

    private String title;
    private int rate;
}

