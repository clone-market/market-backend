package com.market.product.repository;

import com.market.product.entity.ProductBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductBoardRepository extends JpaRepository<ProductBoard, Long> {
}
