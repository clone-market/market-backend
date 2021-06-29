package com.market.product.repository;

import com.market.product.dto.ProductResponseDto;
import com.market.product.entity.Product;

import java.util.List;

public interface ProductQueryRepository {
    public List<ProductResponseDto> findProduct(String orderKeyword);
}
