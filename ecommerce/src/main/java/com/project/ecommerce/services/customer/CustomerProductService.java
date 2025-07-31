package com.project.ecommerce.services.customer;

import com.project.ecommerce.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {
    List<ProductDto> getAllProducts();
}
