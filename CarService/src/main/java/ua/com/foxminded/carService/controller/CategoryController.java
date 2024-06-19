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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.service.CategoryService;

@RestController
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Operation(summary = "Create a new category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Category created successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		Category createdCategory = categoryService.saveCategory(category);
		return ResponseEntity.ok(createdCategory);
	}

	@Operation(summary = "Update an existing category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Category updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(
			@Parameter(description = "ID of the category to be updated") @PathVariable Long id,
			@RequestBody Category categoryDetails) {
		Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
		return ResponseEntity.ok(updatedCategory);
	}

	@Operation(summary = "Delete a category by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Category deleted successfully", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(
			@Parameter(description = "ID of the category to be deleted") @PathVariable Long id) {
		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Get a category by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Category retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Category.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Category not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategory(
			@Parameter(description = "ID of the category to be retrieved") @PathVariable Long id) {
		Optional<Category> category = categoryService.getCategory(id);
		return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "Get all categories")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Categories retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return ResponseEntity.ok(categories);
	}
}
