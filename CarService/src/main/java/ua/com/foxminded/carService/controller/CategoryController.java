package ua.com.foxminded.carService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		Category createdCategory = categoryService.saveCategory(category);
		return ResponseEntity.ok(createdCategory);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
		Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
		return ResponseEntity.ok(updatedCategory);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable Long id) {
		Optional<Category> category = categoryService.getCategory(id);
		return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
}
