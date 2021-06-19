package com.market.member.dto;

import com.market.member.entity.Address;
import com.market.member.entity.Gender;
import com.market.member.entity.MemberGrade;
import com.market.member.entity.MemberTerms;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberParam {

    private String username;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private LocalDateTime birthDay;

    private Address address;

    private MemberGrade memberGrade;

    private MemberTerms memberTerms;

    @Builder
    public MemberParam(String username, String password, String name, String email, String phoneNumber,
                       Gender gender, LocalDateTime birthDay, Address address, MemberGrade memberGrade, MemberTerms memberTerms) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDay = birthDay;
        this.address = address;
        this.memberGrade = memberGrade;
        this.memberTerms = memberTerms;
    }
}
