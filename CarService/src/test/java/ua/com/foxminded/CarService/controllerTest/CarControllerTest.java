package ua.com.foxminded.CarService.controllerTest;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import ua.com.foxminded.carService.controller.CarController;
import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;

@WebMvcTest(CarController.class)
public class CarControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarService carService;

	@InjectMocks
	private CarController carController;

	private Car car1;
	private Car car2;
	private Make make;
	private Category category;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		make = new Make(1L, "Toyota");
		category = new Category(1L, "SUV");

		car1 = new Car(1L, "obj1", "Camry", 2020, make, Arrays.asList(category));
		car2 = new Car(2L, "obj2", "Corolla", 2021, make, Arrays.asList(category));
	}

//	@Test
//	@WithMockUser(username = "admin", roles = { "USER" })
//	public void testCreateCar() throws Exception {
//		when(carService.saveCar(any(Car.class))).thenReturn(car1);
//
//		mockMvc.perform(post("/api/v1/cars").contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(car1))).andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").value(car1.getId()))
//				.andExpect(jsonPath("$.objectId").value(car1.getObjectId()))
//				.andExpect(jsonPath("$.model").value(car1.getModel()))
//				.andExpect(jsonPath("$.year").value(car1.getYear()))
//				.andExpect(jsonPath("$.make.makeName").value(car1.getMake().getMakeName())).andExpect(
//						jsonPath("$.categories[0].categoryName").value(car1.getCategories().get(0).getCategoryName()));
//	}
//
//	@Test
//	@WithMockUser(username = "admin", roles = { "USER" })
//	public void testUpdateCar() throws Exception {
//		when(carService.updateCar(anyLong(), any(Car.class))).thenReturn(car1);
//
//		mockMvc.perform(put("/api/v1/cars/1").contentType(MediaType.APPLICATION_JSON)
//				.content(new ObjectMapper().writeValueAsString(car1))).andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").value(car1.getId()))
//				.andExpect(jsonPath("$.objectId").value(car1.getObjectId()))
//				.andExpect(jsonPath("$.model").value(car1.getModel()))
//				.andExpect(jsonPath("$.year").value(car1.getYear()))
//				.andExpect(jsonPath("$.make.makeName").value(car1.getMake().getMakeName())).andExpect(
//						jsonPath("$.categories[0].categoryName").value(car1.getCategories().get(0).getCategoryName()));
//	}
//
//	@Test
//	@WithMockUser(username = "admin", roles = { "USER" })
//	public void testDeleteCar() throws Exception {
//		mockMvc.perform(delete("/api/v1/cars/1")).andExpect(status().isNoContent());
//	}
//
//	@Test
//	@WithMockUser(username = "admin", roles = { "USER" })
//	public void testGetCar() throws Exception {
//		when(carService.getCar(anyLong())).thenReturn(Optional.of(car1));
//
//		mockMvc.perform(get("/api/v1/cars/1")).andExpect(status().isOk())
//				.andExpect(jsonPath("$.id").value(car1.getId()))
//				.andExpect(jsonPath("$.objectId").value(car1.getObjectId()))
//				.andExpect(jsonPath("$.model").value(car1.getModel()))
//				.andExpect(jsonPath("$.year").value(car1.getYear()))
//				.andExpect(jsonPath("$.make.makeName").value(car1.getMake().getMakeName())).andExpect(
//						jsonPath("$.categories[0].categoryName").value(car1.getCategories().get(0).getCategoryName()));
//	}
//
//	@Test
//	@WithMockUser(username = "admin", roles = { "USER" })
//	public void testGetCars() throws Exception {
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Car> carsPage = new PageImpl<>(Arrays.asList(car1, car2), pageable, 2);
//		when(carService.getCars(any(Pageable.class))).thenReturn(carsPage);
//
//		mockMvc.perform(get("/api/v1/cars").param("page", "0").param("size", "10")).andExpect(status().isOk())
//				.andExpect(jsonPath("$.content[0].id").value(car1.getId()))
//				.andExpect(jsonPath("$.content[0].objectId").value(car1.getObjectId()))
//				.andExpect(jsonPath("$.content[0].model").value(car1.getModel()))
//				.andExpect(jsonPath("$.content[0].year").value(car1.getYear()))
//				.andExpect(jsonPath("$.content[0].make.makeName").value(car1.getMake().getMakeName()))
//				.andExpect(jsonPath("$.content[0].categories[0].categoryName")
//						.value(car1.getCategories().get(0).getCategoryName()))
//				.andExpect(jsonPath("$.content[1].id").value(car2.getId()))
//				.andExpect(jsonPath("$.content[1].objectId").value(car2.getObjectId()))
//				.andExpect(jsonPath("$.content[1].model").value(car2.getModel()))
//				.andExpect(jsonPath("$.content[1].year").value(car2.getYear()))
//				.andExpect(jsonPath("$.content[1].make.makeName").value(car2.getMake().getMakeName()))
//				.andExpect(jsonPath("$.content[1].categories[0].categoryName")
//						.value(car2.getCategories().get(0).getCategoryName()));
//	}
//
//	@Test
//	@WithMockUser(username = "admin", roles = { "USER" })
//	public void testSearchCars() throws Exception {
//		Pageable pageable = PageRequest.of(0, 10);
//		Page<Car> carsPage = new PageImpl<>(Arrays.asList(car1, car2), pageable, 2);
//		when(carService.searchCars(any(), any(), any(), any(), any(), any(Pageable.class))).thenReturn(carsPage);
//
//		mockMvc.perform(get("/api/v1/cars/search").param("manufacturer", "Toyota").param("model", "Camry")
//				.param("minYear", "2019").param("maxYear", "2021").param("category", "SUV").param("page", "0")
//				.param("size", "10")).andExpect(status().isOk())
//				.andExpect(jsonPath("$.content[0].id").value(car1.getId()))
//				.andExpect(jsonPath("$.content[0].objectId").value(car1.getObjectId()))
//				.andExpect(jsonPath("$.content[0].model").value(car1.getModel()))
//				.andExpect(jsonPath("$.content[0].year").value(car1.getYear()))
//				.andExpect(jsonPath("$.content[0].make.makeName").value(car1.getMake().getMakeName()))
//				.andExpect(jsonPath("$.content[0].categories[0].categoryName")
//						.value(car1.getCategories().get(0).getCategoryName()))
//				.andExpect(jsonPath("$.content[1].id").value(car2.getId()))
//				.andExpect(jsonPath("$.content[1].objectId").value(car2.getObjectId()))
//				.andExpect(jsonPath("$.content[1].model").value(car2.getModel()))
//				.andExpect(jsonPath("$.content[1].year").value(car2.getYear()))
//				.andExpect(jsonPath("$.content[1].make.makeName").value(car2.getMake().getMakeName()))
//				.andExpect(jsonPath("$.content[1].categories[0].categoryName")
//						.value(car2.getCategories().get(0).getCategoryName()));
//	}
}
