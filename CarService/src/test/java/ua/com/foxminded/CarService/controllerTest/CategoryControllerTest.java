package ua.com.foxminded.CarService.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.carService.controller.CategoryController;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.service.CategoryService;

@WebMvcTest(CategoryController.class)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@Autowired
	private ObjectMapper objectMapper;

	private Category category;

	@BeforeEach
	void setUp() {
		category = new Category(1L, "SUV");
	}

	@Test
	void createCategory_shouldReturnCreatedCategory() throws Exception {
		when(categoryService.saveCategory(any(Category.class))).thenReturn(category);

		mockMvc.perform(post("/api/v1/categories").with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(category)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.categoryId").value(category.getCategoryId()))
				.andExpect(jsonPath("$.categoryName").value(category.getCategoryName()));
	}

	@Test
	void updateCategory_shouldReturnUpdatedCategory() throws Exception {
		when(categoryService.updateCategory(anyLong(), any(Category.class))).thenReturn(category);

		mockMvc.perform(put("/api/v1/categories/{id}", category.getCategoryId()).with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(category)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.categoryId").value(category.getCategoryId()))
				.andExpect(jsonPath("$.categoryName").value(category.getCategoryName()));
	}

	@Test
	void deleteCategory_shouldReturnNoContent() throws Exception {
		mockMvc.perform(delete("/api/v1/categories/{id}", category.getCategoryId()).with(jwt())) // Mock JWT
																									// authentication
				.andExpect(status().isNoContent());
	}

	@Test
	void getCategory_shouldReturnCategory() throws Exception {
		when(categoryService.getCategory(anyLong())).thenReturn(Optional.of(category));

		mockMvc.perform(get("/api/v1/categories/{id}", category.getCategoryId()).with(jwt())) // Mock JWT authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$.categoryId").value(category.getCategoryId()))
				.andExpect(jsonPath("$.categoryName").value(category.getCategoryName()));
	}

	@Test
	void getAllCategories_shouldReturnListOfCategories() throws Exception {
		when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category));

		mockMvc.perform(get("/api/v1/categories").with(jwt())) // Mock JWT authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].categoryId").value(category.getCategoryId()))
				.andExpect(jsonPath("$[0].categoryName").value(category.getCategoryName()));
	}
}