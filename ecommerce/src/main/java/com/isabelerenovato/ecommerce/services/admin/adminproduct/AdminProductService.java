package com.isabelerenovato.ecommerce.services.admin.adminproduct;

import java.util.List;

import com.isabelerenovato.ecommerce.dto.ProductDto;

import io.jsonwebtoken.io.IOException;

public interface AdminProductService {
	
	ProductDto addProduct(ProductDto productDto) throws IOException, java.io.IOException;

	List<ProductDto> getAllProducts();
}
