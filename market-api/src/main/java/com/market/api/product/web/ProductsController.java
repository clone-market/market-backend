package com.market.api.product.web;

import com.market.product.dto.ProductResponseDto;
import com.market.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v2/products")
public class ProductsController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findProductList(@RequestParam String orderKeyword) {
        List<ProductResponseDto> productList = productService.findProductList(orderKeyword);
        return ResponseEntity.ok(productList);
    }
}