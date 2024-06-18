package ua.com.foxminded.CarService.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
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
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController categoryController;

	private Category category1;
	private Category category2;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		category1 = new Category(1L, "Sedan");
		category2 = new Category(2L, "Electric");
	}

	@Test
	public void testCreateCategory() throws Exception {
		when(categoryService.saveCategory(any(Category.class))).thenReturn(category1);

		mockMvc.perform(post("/api/v1/categories").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(category1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.categoryId").value(category1.getCategoryId()))
				.andExpect(jsonPath("$.categoryName").value(category1.getCategoryName()));
	}

	@Test
	public void testUpdateCategory() throws Exception {
		when(categoryService.updateCategory(anyLong(), any(Category.class))).thenReturn(category1);

		mockMvc.perform(put("/api/v1/categories/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(category1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.categoryId").value(category1.getCategoryId()))
				.andExpect(jsonPath("$.categoryName").value(category1.getCategoryName()));
	}

	@Test
	public void testDeleteCategory() throws Exception {
		mockMvc.perform(delete("/api/v1/categories/1")).andExpect(status().isNoContent());
	}

	@Test
	public void testGetCategory() throws Exception {
		when(categoryService.getCategory(anyLong())).thenReturn(Optional.of(category1));

		mockMvc.perform(get("/api/v1/categories/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.categoryId").value(category1.getCategoryId()))
				.andExpect(jsonPath("$.categoryName").value(category1.getCategoryName()));
	}

	@Test
	public void testGetCategories() throws Exception {
		List<Category> categories = Arrays.asList(category1, category2);
		when(categoryService.getAllCategories()).thenReturn(categories);

		mockMvc.perform(get("/api/v1/categories")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].categoryId").value(category1.getCategoryId()))
				.andExpect(jsonPath("$[0].categoryName").value(category1.getCategoryName()))
				.andExpect(jsonPath("$[1].categoryId").value(category2.getCategoryId()))
				.andExpect(jsonPath("$[1].categoryName").value(category2.getCategoryName()));
	}
}
