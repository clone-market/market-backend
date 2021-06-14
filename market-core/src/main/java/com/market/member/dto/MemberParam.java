package com.market.member.dto;

import com.market.member.entity.Gender;
import com.market.member.entity.MemberGrade;
import com.market.member.entity.Yn;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberParam {

    private String username;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private LocalDateTime birthDay;

    private Yn SmsYn;

    private Yn WebYn;

    private MemberGrade memberGrade;
}
