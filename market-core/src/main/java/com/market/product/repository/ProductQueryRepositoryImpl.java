package com.market.product.repository;

import com.market.product.dto.ProductResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.market.product.entity.QProduct.product;

@RequiredArgsConstructor
public class ProductQueryRepositoryImpl implements ProductQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductResponseDto> findProduct(String orderKeyword) {
        JPAQuery<ProductResponseDto> query = jpaQueryFactory
                .select(Projections.constructor(
                        ProductResponseDto.class,
                        product.name,
                        product.price,
                        product.discount.discountRate,
                        product.shortDescription,
                        product.views
                ))
                .from(product)
                .leftJoin(product.discount);

        orderBuilder(orderKeyword, query);

        return query.fetch();
    }
    private void orderBuilder(String keyword, JPAQuery<ProductResponseDto> query) {
        switch(keyword) {
            case "s": query.orderBy(product.goods.desc()); break;
            case "c": query.orderBy(product.createdDate.desc()); break;
            case "v": query.orderBy(product.views.desc()); break;
            case "hp": query.orderBy(product.price.desc()); break;
            case "lp": query.orderBy(product.price.asc()); break;
        }
    }
}