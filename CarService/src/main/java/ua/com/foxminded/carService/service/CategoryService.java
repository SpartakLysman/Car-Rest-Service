package ua.com.foxminded.carService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.foxminded.carService.exception.ResourceNotFoundException;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Category updateCategory(Long id, Category categoryDetails) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));

		category.setCategoryName(categoryDetails.getCategoryName());

		return categoryRepository.save(category);
	}

	public void deleteCategory(Long id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
		categoryRepository.delete(category);
	}

	public Optional<Category> getCategory(Long id) {
		return categoryRepository.findById(id);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public Category findByName(String name) {
		return categoryRepository.findByCategoryName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Make not found with name: " + name));
	}
}