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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDateTime birthDay;

    @Column(name = "receive_ads_by_sms", nullable = false)
    private Yn SmsYn;

    @Column(name = "receive_ads_by_web", nullable = false)
    private Yn WebYn;

    @NotNull
    @OneToOne(fetch = LAZY, cascade = ALL)
    private Point point;

    @NotNull
    @OneToOne(fetch = LAZY, optional = false)
    private MemberGrade grade;


    @Builder
    public Member(Long id, String username, String password, String name, String email, String phoneNumber,
                  Gender gender, LocalDateTime birthDay, Yn SmsYn, Yn WebYn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDay = birthDay;
        this.SmsYn = SmsYn;
        this.WebYn = WebYn;
    }
}
