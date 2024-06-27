package ua.com.foxminded.CarService.repositoryTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoryRepositoryTest {

//	@Autowired
//	private CategoryRepository categoryRepository;
//
//	@BeforeEach
//	void setUp() {
//		categoryRepository.deleteAll();
//	}
//
//	@Test
//	void testSaveCategory() {
//		// Given
//		Category category = new Category("SUV");
//
//		// When
//		Category savedCategory = categoryRepository.save(category);
//
//		// Then
//		assertThat(savedCategory.getCategoryId()).isNotNull();
//		assertThat(savedCategory.getCategoryName()).isEqualTo(category.getCategoryName());
//	}
//
//	@Test
//	void testFindByCategoryName() {
//		Category category = new Category("Sedan");
//		categoryRepository.save(category);
//
//		Optional<Category> foundCategory = categoryRepository.findByCategoryName("Sedan");
//
//		assertTrue(foundCategory.isPresent());
//		assertThat(foundCategory.get().getCategoryName()).isEqualTo(category.getCategoryName());
//	}
//
//	@Test
//	void testFindByCategoryName_NotFound() {
//		Optional<Category> foundCategory = categoryRepository.findByCategoryName("NonExistentCategory");
//
//		assertFalse(foundCategory.isPresent());
//	}
//
//	@Test
//	void testDeleteCategory() {
//		Category category = new Category("Hatchback");
//		category = categoryRepository.save(category);
//		Long categoryId = category.getCategoryId();
//
//		categoryRepository.deleteById(categoryId);
//
//		assertFalse(categoryRepository.findById(categoryId).isPresent());
//	}
}
