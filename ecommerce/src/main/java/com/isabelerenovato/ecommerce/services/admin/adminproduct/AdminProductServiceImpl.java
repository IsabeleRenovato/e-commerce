package com.isabelerenovato.ecommerce.services.admin.adminproduct;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.isabelerenovato.ecommerce.dto.ProductDto;
import com.isabelerenovato.ecommerce.entity.Category;
import com.isabelerenovato.ecommerce.entity.Product;
import com.isabelerenovato.ecommerce.repository.CategoryRepository;
import com.isabelerenovato.ecommerce.repository.ProductRepository;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService{

		private final ProductRepository productRepository;
		
		private final CategoryRepository categoryRepository;
		
		public ProductDto addProduct(ProductDto productDto) throws IOException, java.io.IOException {
			Product product = new Product();
			product.setName(productDto.getName());
			product.setDescription(productDto.getDescription());
			product.setPrice(productDto.getPrice());
			product.setImg(productDto.getImg().getBytes());
			
			Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
			
			product.setCategory(category);
			return productRepository.save(product).getDto();
		}
		
		public List<ProductDto> getAllProducts(){
			List<Product> products = productRepository.findAll();
			return products.stream().map(Product::getDto).collect(Collectors.toList());
		}

		
}
