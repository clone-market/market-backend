package com.market.api.user.dto.findInfo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class FindPwdParam {

    @NotBlank(message = "이름을 입력하세요.")
    private String name;

    @NotBlank(message = "아이디를 입력하세요")
    private String username;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

}
