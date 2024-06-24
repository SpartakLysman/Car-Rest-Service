package ua.com.foxminded.CarService.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.repository.CategoryRepository;
import ua.com.foxminded.carService.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	private Category testCategory;

	@BeforeEach
	void setUp() {
		testCategory = new Category(1L, "SUV");
	}

	@Test
	void saveCategoryTest() {
		when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

		Category savedCategory = categoryService.saveCategory(testCategory);

		assertThat(savedCategory).isNotNull();
		assertThat(savedCategory.getCategoryId()).isEqualTo(testCategory.getCategoryId());
		assertThat(savedCategory.getCategoryName()).isEqualTo(testCategory.getCategoryName());

		verify(categoryRepository, times(1)).save(any(Category.class));
	}

	@Test
	void updateCategoryTest() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));
		when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

		Category updatedCategory = new Category();
		updatedCategory.setCategoryName("Sedan");

		Category result = categoryService.updateCategory(1L, updatedCategory);

		assertThat(result).isNotNull();
		assertThat(result.getCategoryName()).isEqualTo(updatedCategory.getCategoryName());

		verify(categoryRepository, times(1)).findById(1L);
		verify(categoryRepository, times(1)).save(any(Category.class));
	}

	@Test
	void deleteCategoryTest() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

		categoryService.deleteCategory(1L);

		verify(categoryRepository, times(1)).findById(1L);
		verify(categoryRepository, times(1)).delete(any(Category.class));
	}

	@Test
	void getCategoryTest() {
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory));

		Optional<Category> result = categoryService.getCategory(1L);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testCategory);

		verify(categoryRepository, times(1)).findById(1L);
	}

	@Test
	void getAllCategoriesTest() {
		List<Category> categories = new ArrayList<>();
		categories.add(testCategory);
		when(categoryRepository.findAll()).thenReturn(categories);

		List<Category> result = categoryService.getAllCategories();

		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(testCategory);

		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	void findByNameTest() {
		when(categoryRepository.findByCategoryName("SUV")).thenReturn(Optional.of(testCategory));

		Category result = categoryService.findByName("SUV");

		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(testCategory);

		verify(categoryRepository, times(1)).findByCategoryName("SUV");
	}
}
