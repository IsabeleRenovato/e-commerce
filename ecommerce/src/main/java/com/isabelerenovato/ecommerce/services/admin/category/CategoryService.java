package com.isabelerenovato.ecommerce.services.admin.category;

import com.isabelerenovato.ecommerce.dto.CategoryDto;
import com.isabelerenovato.ecommerce.entity.Category;

public interface CategoryService {
	
	Category createcategory(CategoryDto categoryDto);
}
