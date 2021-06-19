package com.market.member.dto;

import com.market.member.entity.Yn;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TermsCreationParam {

    private Yn agreeYn;

    private Yn consentReqYn;

    private Yn consentOptYn;

    private Yn smsYn;

    private Yn mailYn;

    private Yn fourteenYn;
}
