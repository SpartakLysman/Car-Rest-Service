package ua.com.foxminded.CarService.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import ua.com.foxminded.carService.controller.ManufacturerModelController;
import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;
import ua.com.foxminded.carService.service.CategoryService;
import ua.com.foxminded.carService.service.MakeService;

@WebMvcTest(ManufacturerModelController.class)
@ExtendWith(MockitoExtension.class)
public class ManufacturerModelControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MakeService makeService;

	@MockBean
	private CarService carService;

	@MockBean
	private CategoryService categoryService;

	@Autowired
	private ObjectMapper objectMapper;

	private Make make;
	private Category category;
	private Car car;

	@BeforeEach
	void setUp() {
		make = new Make(1L, "Toyota");
		category = new Category(1L, "SUV");
		car = new Car("Model X", 2024, make, List.of(category));
	}

	@Test
	void createCarWithManufacturerModelAndYear_shouldReturnCreatedCar() throws Exception {
		List<String> categories = List.of("SUV");

		when(makeService.findByName(anyString())).thenReturn(make);
		when(categoryService.findByName(anyString())).thenReturn(category);
		when(carService.saveCar(any(Car.class))).thenReturn(car);
		when(makeService.saveMake(any(Make.class))).thenReturn(make);
		when(categoryService.saveCategory(any(Category.class))).thenReturn(category);

		mockMvc.perform(post("/api/v1/manufacturers/{manufacturer}/models/{model}/{year}", "Toyota", "Model X", 2024)
				.with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categories)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.year").value(car.getYear()))
				.andExpect(jsonPath("$.make.makeName").value(car.getMake().getMakeName()))
				.andExpect(jsonPath("$.categoryName").value(car.getCategories().get(0).getCategoryName()));
	}

	@Test
	void createCarWithManufacturerModelAndYear_shouldReturnBadRequestWhenCategoryNotFound() throws Exception {
		List<String> categories = List.of("NonExistentCategory");

		when(makeService.findByName(anyString())).thenReturn(make);
		when(categoryService.findByName(anyString())).thenReturn(null);
		when(makeService.saveMake(any(Make.class))).thenReturn(make);
		when(categoryService.saveCategory(any(Category.class))).thenReturn(new Category("NonExistentCategory"));
		when(carService.saveCar(any(Car.class))).thenReturn(car);

		mockMvc.perform(post("/api/v1/manufacturers/{manufacturer}/models/{model}/{year}", "Toyota", "Model X", 2024)
				.with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categories)))
				.andExpect(status().isCreated()) // Expecting creation with a new category
				.andExpect(jsonPath("$.model").value(car.getModel())).andExpect(jsonPath("$.year").value(car.getYear()))
				.andExpect(jsonPath("$.make.makeName").value(car.getMake().getMakeName()))
				.andExpect(jsonPath("$.categoryName").value("NonExistentCategory"));
	}

	@Test
	void createCarWithManufacturerModelAndYear_shouldReturnNotFoundWhenManufacturerNotFound() throws Exception {
		List<String> categories = List.of("SUV");

		when(makeService.findByName(anyString())).thenReturn(null);
		when(makeService.saveMake(any(Make.class))).thenReturn(make);
		when(categoryService.findByName(anyString())).thenReturn(category);
		when(categoryService.saveCategory(any(Category.class))).thenReturn(category);
		when(carService.saveCar(any(Car.class))).thenReturn(car);

		mockMvc.perform(post("/api/v1/manufacturers/{manufacturer}/models/{model}/{year}", "Toyota", "Model X", 2024)
				.with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categories)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.year").value(car.getYear()))
				.andExpect(jsonPath("$.make.makeName").value(car.getMake().getMakeName())).andExpect(
						jsonPath("$.categories[0].categoryName").value(car.getCategories().get(0).getCategoryName()));
	}

	@Test
	void createCarWithManufacturerModelAndYear_shouldReturnInternalServerError() throws Exception {
		List<String> categories = List.of("SUV");

		when(makeService.findByName(anyString())).thenReturn(make);
		when(categoryService.findByName(anyString())).thenReturn(category);
		when(carService.saveCar(any(Car.class))).thenThrow(new RuntimeException("Internal server error"));

		mockMvc.perform(post("/api/v1/manufacturers/{manufacturer}/models/{model}/{year}", "Toyota", "Model X", 2024)
				.with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categories)))
				.andExpect(status().isInternalServerError());
	}
}
