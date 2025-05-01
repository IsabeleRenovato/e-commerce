package com.isabelerenovato.ecommerce.services.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isabelerenovato.ecommerce.dto.CategoryDto;
import com.isabelerenovato.ecommerce.entity.Category;
import com.isabelerenovato.ecommerce.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;

	public Category createcategory(CategoryDto categoryDto) {
		Category category = new Category();
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		
		return categoryRepository.save(category);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
}
