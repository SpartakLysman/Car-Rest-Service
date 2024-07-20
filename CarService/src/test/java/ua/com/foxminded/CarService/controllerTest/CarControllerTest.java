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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.carService.controller.CarController;
import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;

@WebMvcTest(CarController.class)
@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	@Autowired
	private ObjectMapper objectMapper;

	private Car car;
	private Make make;
	private Category category;

	@BeforeEach
	void setUp() {
		make = new Make(1L, "Toyota");
		category = new Category(1L, "SUV");
		car = new Car(1L, "objectId", "Camry", 2020, make, Arrays.asList(category));
	}

	@Test
	void createCar_shouldReturnCreatedCar() throws Exception {
		when(carService.saveCar(any(Car.class))).thenReturn(car);

		mockMvc.perform(post("/api/v1/cars").with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(car)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(car.getId()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.year").value(car.getYear()));
	}

	@Test
	void updateCar_shouldReturnUpdatedCar() throws Exception {
		when(carService.updateCar(anyLong(), any(Car.class))).thenReturn(car);

		mockMvc.perform(put("/api/v1/cars/{id}", car.getId()).with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(car)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(car.getId()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.year").value(car.getYear()));
	}

	@Test
	void deleteCar_shouldReturnNoContent() throws Exception {
		mockMvc.perform(delete("/api/v1/cars/{id}", car.getId()).with(jwt())) // Mock JWT authentication
				.andExpect(status().isNoContent());
	}

	@Test
	void getCar_shouldReturnCar() throws Exception {
		when(carService.getCar(anyLong())).thenReturn(Optional.of(car));

		mockMvc.perform(get("/api/v1/cars/{id}", car.getId()).with(jwt())) // Mock JWT authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(car.getId()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.year").value(car.getYear()));
	}

	@Test
	void getCars_shouldReturnPageOfCars() throws Exception {
		when(carService.getCars(any())).thenReturn(new PageImpl<>(Arrays.asList(car)));

		mockMvc.perform(get("/api/v1/cars").with(jwt())) // Mock JWT authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].id").value(car.getId()))
				.andExpect(jsonPath("$.content[0].model").value(car.getModel()))
				.andExpect(jsonPath("$.content[0].year").value(car.getYear()));
	}

	@Test
	void searchCars_shouldReturnPageOfCars() throws Exception {
		when(carService.searchCars(any(), any(), any(), any(), any(), any()))
				.thenReturn(new PageImpl<>(Arrays.asList(car)));

		mockMvc.perform(get("/api/v1/cars/search").param("manufacturer", "Toyota").param("model", "Camry")
				.param("minYear", "2019").param("maxYear", "2021").param("category", "SUV").with(jwt())) // Mock JWT
																											// authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$.content[0].id").value(car.getId()))
				.andExpect(jsonPath("$.content[0].model").value(car.getModel()))
				.andExpect(jsonPath("$.content[0].year").value(car.getYear()));
	}
}