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
    public Product(Long id, String name, int stockQuantity, String thumbnailUrl, String shortDescription, int price, String description, ProductType productType, Discount discount, Category category, List<ProductBoard> productBoards) {
        this.id = id;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.thumbnailUrl = thumbnailUrl;
        this.shortDescription = shortDescription;
        this.price = price;
        this.description = description;
        this.productType = productType;
        this.discount = discount;
        this.category = category;
        this.productBoards = productBoards;
    }
}
