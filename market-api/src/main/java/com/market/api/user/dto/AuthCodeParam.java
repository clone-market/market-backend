package com.market.api.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AuthCodeParam {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String authCode;
}
