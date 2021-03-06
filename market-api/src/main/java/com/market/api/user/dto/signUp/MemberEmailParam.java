package com.market.api.user.dto.signUp;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class MemberEmailParam {

    @Email
    @NotNull
    private String email;
}