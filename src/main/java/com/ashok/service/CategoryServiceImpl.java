package com.ashok.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashok.entity.Category;
import com.ashok.exception.APIException;
import com.ashok.exception.ResourceNotFoundException;
import com.ashok.payload.CategoryDTO;
import com.ashok.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDTO createCategory(Category category) {
		// TODO Auto-generated method stub
		Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
		if (savedCategory != null) {
			throw new APIException("Category with the name '" + category.getCategoryName() + "' already exists !!!");
		}
		savedCategory = categoryRepository.save(category);

		return modelMapper.map(savedCategory, CategoryDTO.class);

	}

	@Override
	public List<CategoryDTO> getCategories() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryRepository.findAll();
		List<CategoryDTO> categoryDTOs = categories.stream()
				.map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
		return categoryDTOs;
	}

	@Override
	public CategoryDTO updateCategory(Category category, Long categoryId) {
		// TODO Auto-generated method stub
		Category savedCategory = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

		category.setCategoryId(categoryId);

		savedCategory = categoryRepository.save(category);

		return modelMapper.map(savedCategory, CategoryDTO.class);
	}

	@Override
	public String deleteCategory(Long categoryId) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		categoryRepository.delete(category);

		return "Category with categoryId: " + categoryId + " deleted successfully !!!";
	
	}
}
