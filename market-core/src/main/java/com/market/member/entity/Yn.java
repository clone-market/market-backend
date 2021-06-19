package com.market.member.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.market.member.exception.EnumValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Yn {
    Y("수락"),
    N("거부");

    private final String title;

    @JsonCreator
    public static Yn create(String requestValue) {
        for (Yn value : values()) {
            if (requestValue.equals(value.name())) {
                return value;
            }
        }
        throw new EnumValidationException(requestValue);
    }

}