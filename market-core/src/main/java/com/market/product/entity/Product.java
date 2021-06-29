package com.market.product.entity;


import com.market.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private int stockQuantity;

    @NotNull
    private String thumbnailUrl;

    private String shortDescription;

    @NotNull
    private int views = 0;

    @NotNull
    private int goods = 0;

    @NotNull
    private int price;

    @NotNull
    @Lob @Basic(fetch = LAZY)
    private String description;

    @Enumerated(STRING)
    private ProductType productType;

    @OneToOne(fetch = LAZY, mappedBy = "product")
    private Discount discount;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductBoard> productBoards;

    @Builder
    public Product(Long id, @NotNull String name, @NotNull int stockQuantity, @NotNull String thumbnailUrl, String shortDescription, @NotNull int views, @NotNull int goods, @NotNull int price, @NotNull String description, ProductType productType, Discount discount, @NotNull Category category, List<ProductBoard> productBoards) {
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.thumbnailUrl = thumbnailUrl;
        this.shortDescription = shortDescription;
        this.views = views;
        this.goods = goods;
        this.price = price;
        this.description = description;
        this.productType = productType;
        this.discount = discount;
        this.category = category;
        this.productBoards = productBoards;
    }
}
