package com.market.product.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductBoardType {
    REVIEW("후기"),
    QUESTION("문의");

    private final String title;
}
