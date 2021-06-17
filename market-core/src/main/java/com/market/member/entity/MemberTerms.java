package com.market.member.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(STRING)
    private Yn agreeYn;

    @NotNull
    @Enumerated(STRING)
    private Yn consentReqYn;

    @NotNull
    @Enumerated(STRING)
    private Yn fourteenYn;

    @NotNull
    @Enumerated(STRING)
    private Yn consentOptYn;

    @NotNull
    @Enumerated(STRING)
    private Yn smsYn;

    @NotNull
    @Enumerated(STRING)
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
