package com.market.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponseDto {
    private String productName;
    private int price;
    private int discountRate;
    private String shortDescription;
    private int views;

    @Builder
    public ProductResponseDto(String productName, int price, int discountRate, String shortDescription, int views) {
        this.productName = productName;
        this.price = price;
        this.discountRate = discountRate;
        this.shortDescription = shortDescription;
        this.views = views;
    }
}