package com.market.member.entity;

import com.market.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Enumerated(STRING)
    private Gender gender;

    @NotNull
    private LocalDateTime birthDay;

    @NotNull
    @OneToOne(fetch = LAZY, cascade = ALL)
    private Point point;

    @NotNull
    @OneToOne(fetch = LAZY)
    private MemberGrade grade;
    
    @NotNull
    @OneToOne(fetch = LAZY, cascade = ALL)
    private MemberTerms terms;

    @Builder
    public Member(@NotNull String username, @NotNull String password, @NotNull String name,
                  @NotNull String email, @NotNull String phoneNumber, @NotNull Gender gender,
                  @NotNull MemberGrade grade, @NotNull MemberTerms terms, @NotNull LocalDateTime birthDay) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.grade = grade;
        this.terms = terms;
        this.birthDay = birthDay;
        this.point = new Point();
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
