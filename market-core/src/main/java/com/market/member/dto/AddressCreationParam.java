package com.market.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressCreationParam {

    private String zipCode;

    private String streetNameAddress;

    private String lotNumberAddress;

    private String detailedAddress;
}
