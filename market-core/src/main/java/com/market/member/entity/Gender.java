package com.market.member.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.market.member.exception.EnumValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    M("남성"),
    F("여성"),
    N("없음");

    private final String title;

    @JsonCreator
    public static Gender create(String requestValue) {
        for (Gender value : values()) {
            if (requestValue.equals(value.name())) {
                return value;
            }
        }
        throw new EnumValidationException(requestValue);
    }
}
