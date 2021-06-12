package com.market.member.domain;

import com.market.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
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
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private LocalDateTime birthDay;
    @Column(name = "receive_ads_by_sms", nullable = false)
    private Yn SmsYn;
    @Column(name = "receive_ads_by_web", nullable = false)
    private Yn WebYn;

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
