package com.market.member.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Yn agreeYn;

    @NotNull
    private Yn consentReqYn;

    @NotNull
    private Yn fourteenYn;

    @NotNull
    private Yn consentOptYn;

    @NotNull
    private Yn smsYn;

    @NotNull
    private Yn mailYn;

    @Builder
    public MemberTerms(@NotNull Yn agreeYn, @NotNull Yn consentReqYn, @NotNull Yn fourteenYn,
                       @NotNull Yn consentOptYn, @NotNull Yn smsYn, @NotNull Yn mailYn) {
        this.agreeYn = agreeYn;
        this.consentReqYn = consentReqYn;
        this.fourteenYn = fourteenYn;
        this.consentOptYn = consentOptYn;
        this.smsYn = smsYn;
        this.mailYn = mailYn;
    }
}
