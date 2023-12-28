package com.ashok.service;


import java.util.List;

import com.ashok.entity.Category;
import com.ashok.payload.CategoryDTO;

public interface CategoryService {
	CategoryDTO createCategory(Category category);

	List<CategoryDTO> getCategories();

	CategoryDTO updateCategory(Category category, Long categoryId);

	String deleteCategory(Long categoryId);
}
