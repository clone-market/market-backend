package com.market.member.dto;

import com.market.member.entity.Address;
import com.market.member.entity.Gender;
import com.market.member.entity.Grade;
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

    private Grade grade;

    private MemberTerms memberTerms;

    @Builder
    public MemberParam(String username, String password, String name, String email, String phoneNumber,
                       Gender gender, LocalDateTime birthDay, Address address, Grade grade, MemberTerms memberTerms) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDay = birthDay;
        this.address = address;
        this.grade = grade;
        this.memberTerms = memberTerms;
    }
}
