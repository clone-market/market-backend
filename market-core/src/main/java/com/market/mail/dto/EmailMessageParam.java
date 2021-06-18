package com.market.mail.dto;

import lombok.Data;

@Data
public class EmailMessageParam {

    private final String to;

    private final String subject;

    private final String message;
}
