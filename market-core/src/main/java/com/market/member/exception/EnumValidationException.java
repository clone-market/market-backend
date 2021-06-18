package com.market.member.exception;

import lombok.Getter;

@Getter
public class EnumValidationException extends RuntimeException {

    private final String value;

    public EnumValidationException(String value) {
        super(value);
        this.value = value;
    }
}
