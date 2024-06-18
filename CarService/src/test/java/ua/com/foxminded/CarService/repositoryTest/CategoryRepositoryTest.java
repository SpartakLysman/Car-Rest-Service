package ua.com.foxminded.CarService.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.repository.CategoryRepository;

@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@BeforeEach
	void setUp() {
		categoryRepository.deleteAll();
	}

	@Test
	void testSaveCategory() {
		// Given
		Category category = new Category("SUV");

		// When
		Category savedCategory = categoryRepository.save(category);

		// Then
		assertThat(savedCategory.getCategoryId()).isNotNull();
		assertThat(savedCategory.getCategoryName()).isEqualTo(category.getCategoryName());
	}

	@Test
	void testFindByCategoryName() {
		Category category = new Category("Sedan");
		categoryRepository.save(category);

		Optional<Category> foundCategory = categoryRepository.findByCategoryName("Sedan");

		assertTrue(foundCategory.isPresent());
		assertThat(foundCategory.get().getCategoryName()).isEqualTo(category.getCategoryName());
	}

	@Test
	void testFindByCategoryName_NotFound() {
		Optional<Category> foundCategory = categoryRepository.findByCategoryName("NonExistentCategory");

		assertFalse(foundCategory.isPresent());
	}

	@Test
	void testDeleteCategory() {
		Category category = new Category("Hatchback");
		category = categoryRepository.save(category);
		Long categoryId = category.getCategoryId();

		categoryRepository.deleteById(categoryId);

		assertFalse(categoryRepository.findById(categoryId).isPresent());
	}
}
