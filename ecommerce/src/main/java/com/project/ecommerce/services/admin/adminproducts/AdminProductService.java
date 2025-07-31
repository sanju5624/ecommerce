package com.project.ecommerce.services.admin.adminproducts;

import com.project.ecommerce.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    boolean deleteProduct(Long id);

}
