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

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.service.CarService;

@RunWith(MockitoJUnitRunner.class)
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	@Autowired
	private ObjectMapper objectMapper;

	private Car car;

	@BeforeEach
	void setUp() {
		car = new Car();
		car.setId("4q7L00AU2S");
		car.setYear(2001);
		car.setModel("Corolla");
	}

	@Test
	void createCarTest() throws Exception {
		when(carService.saveCar(any(Car.class))).thenReturn(car);

		mockMvc.perform(post("/api/v1/cars").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(car))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.objectId").value(car.getId()))
				.andExpect(jsonPath("$.model").value(car.getModel()));
	}

	@Test
	void getCarByIdTest() throws Exception {
		when(carService.getCar(anyLong())).thenReturn(Optional.of(car));

		mockMvc.perform(get("/api/v1/cars/{id}", 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$.objectId").value(car.getId()))
				.andExpect(jsonPath("$.model").value(car.getModel()));
	}

	@Test
	void updateCarTest() throws Exception {
		when(carService.updateCar(anyLong(), any(Car.class))).thenReturn(car);

		mockMvc.perform(put("/api/v1/cars/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(car))).andExpect(status().isOk())
				.andExpect(jsonPath("$.objectId").value(car.getId()))
				.andExpect(jsonPath("$.model").value(car.getModel()));
	}

	@Test
	void deleteCarTest() throws Exception {
		mockMvc.perform(delete("/api/v1/cars/{id}", 1L)).andExpect(status().isNoContent());
	}
}
