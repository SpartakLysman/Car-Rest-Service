package ua.com.foxminded.CarService.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.repository.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void contextLoads() {
		assertThat(categoryRepository).isNotNull();
	}

	@Test
	public void saveAndFindByCategoryName_shouldReturnCategory() {
		// Given
		Category category = new Category();
		category.setCategoryName("SUV");
		categoryRepository.save(category);

		// When
		Optional<Category> foundCategory = categoryRepository.findByCategoryName("SUV");

		// Then
		assertThat(foundCategory).isPresent();
		assertThat(foundCategory.get().getCategoryName()).isEqualTo("SUV");
	}

	@Test
	public void findByCategoryName_shouldReturnEmpty_whenCategoryNotFound() {
		// When
		Optional<Category> foundCategory = categoryRepository.findByCategoryName("NonExistentCategory");

		// Then
		assertThat(foundCategory).isEmpty();
	}
}