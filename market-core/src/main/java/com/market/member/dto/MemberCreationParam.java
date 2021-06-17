package com.market.member.dto;

import com.market.member.entity.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberCreationParam {

    private String username;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private LocalDateTime birthday;

    private AddressCreationParam addressCreationParam;

    private TermsCreationParam termsCreationParam;
}
