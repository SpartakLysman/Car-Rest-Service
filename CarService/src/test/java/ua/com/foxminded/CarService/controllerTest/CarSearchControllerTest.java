package ua.com.foxminded.CarService.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;

@RunWith(MockitoJUnitRunner.class)
public class CarSearchControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	private Car car;
	private Make make;

	@BeforeEach
	void setUp() {
		make = new Make();
		make.setMakeId(1L);
		make.setMakeName("Toyota");

		car = new Car();
		car.setId(1);
		car.setObjectId("99klWfTOAe");
		car.setYear(2001);
		car.setModel("Corolla");
		car.setMake(make);
	}

	@Test
	void searchCarsTest() throws Exception {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Car> carPage = new PageImpl<>(Arrays.asList(car), pageable, 1);
		when(carService.searchCars("Toyota", null, null, null, null, pageable)).thenReturn(carPage);

		mockMvc.perform(get("/api/v1/cars").param("manufacturer", "Toyota").param("page", "0").param("size", "10")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].objectId").value(car.getId()))
				.andExpect(jsonPath("$.content[0].model").value(car.getModel()))
				.andExpect(jsonPath("$.content[0].make.makeName").value(make.getMakeName()));
	}
}
