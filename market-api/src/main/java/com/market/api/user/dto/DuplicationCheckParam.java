package com.market.api.user.dto;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
public class DuplicationCheckParam {

    @NotNull(message = "모드를 선택하세요.")
    private CheckMode mode;

    private String username;

    private String email;
}
