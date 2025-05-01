package com.isabelerenovato.ecommerce.services.admin.category;

import java.util.List;

import com.isabelerenovato.ecommerce.dto.CategoryDto;
import com.isabelerenovato.ecommerce.entity.Category;

public interface CategoryService {
	
	Category createcategory(CategoryDto categoryDto);
	
	List<Category> getAllCategories();
}
