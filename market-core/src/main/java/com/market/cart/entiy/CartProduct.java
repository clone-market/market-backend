package com.market.cart.entiy;

import com.market.common.domain.BaseTimeEntity;
import com.market.product.entity.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Getter @NoArgsConstructor(access = PROTECTED)
public class CartProduct extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private int count;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Cart cart;

    @NotNull
    @ManyToOne(fetch = LAZY, optional = false)
    private Product product;
}
