package com.market.mail.service;

import com.market.mail.dto.EmailMessageParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile(value = "dev")
public class ConsoleEmailService implements EmailService {

    @Override
    public void send(EmailMessageParam messageParam) {
        log.info("{}에게 메일이 도착했습니다. \n {}", messageParam.getTo(), messageParam.getMessage());
    }
}
