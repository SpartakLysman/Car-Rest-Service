package ua.com.foxminded.CarService.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;
import ua.com.foxminded.carService.service.MakeService;

@WebMvcTest
@AutoConfigureMockMvc
public class ManufacturerModelControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MakeService makeService;

	@MockBean
	private CarService carService;

	private Car car;
	private Make make;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

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
	void createCarWithManufacturerModelAndYearTest() throws Exception {
		List<String> categories = new ArrayList<>();
		categories.add("Sedan");

		when(makeService.findByName(anyString())).thenReturn(make);
		when(carService.saveCar(any(Car.class))).thenReturn(car);

		mockMvc.perform(post("/api/v1/manufacturers/{manufacturer}/models/{model}/{year}", "Toyota", "Corolla", 2001)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categories)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.objectId").value(car.getObjectId()))
				.andExpect(jsonPath("$.model").value(car.getModel()))
				.andExpect(jsonPath("$.make.makeName").value(make.getMakeName()));
	}
}
