package com.market.mail.service;

import com.market.mail.dto.EmailMessageParam;

public interface EmailService {

    void send(EmailMessageParam messageParam);
}
