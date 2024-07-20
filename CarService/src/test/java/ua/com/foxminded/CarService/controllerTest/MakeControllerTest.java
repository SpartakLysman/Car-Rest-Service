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

import ua.com.foxminded.carService.controller.MakeController;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.MakeService;

@WebMvcTest(MakeController.class)
@ExtendWith(MockitoExtension.class)
public class MakeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MakeService makeService;

	@Autowired
	private ObjectMapper objectMapper;

	private Make make;

	@BeforeEach
	void setUp() {
		make = new Make(1L, "Toyota");
	}

	@Test
	void createMake_shouldReturnCreatedMake() throws Exception {
		when(makeService.saveMake(any(Make.class))).thenReturn(make);

		mockMvc.perform(post("/api/v1/manufacturers").with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(make)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.makeId").value(make.getMakeId()))
				.andExpect(jsonPath("$.makeName").value(make.getMakeName()));
	}

	@Test
	void updateMake_shouldReturnUpdatedMake() throws Exception {
		when(makeService.updateMake(anyLong(), any(Make.class))).thenReturn(make);

		mockMvc.perform(put("/api/v1/manufacturers/{id}", make.getMakeId()).with(jwt()) // Mock JWT authentication
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(make)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.makeId").value(make.getMakeId()))
				.andExpect(jsonPath("$.makeName").value(make.getMakeName()));
	}

	@Test
	void deleteMake_shouldReturnNoContent() throws Exception {
		mockMvc.perform(delete("/api/v1/manufacturers/{id}", make.getMakeId()).with(jwt())) // Mock JWT authentication
				.andExpect(status().isNoContent());
	}

	@Test
	void getMake_shouldReturnMake() throws Exception {
		when(makeService.getMake(anyLong())).thenReturn(Optional.of(make));

		mockMvc.perform(get("/api/v1/manufacturers/{id}", make.getMakeId()).with(jwt())) // Mock JWT authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$.makeId").value(make.getMakeId()))
				.andExpect(jsonPath("$.makeName").value(make.getMakeName()));
	}

	@Test
	void getAllMakes_shouldReturnListOfMakes() throws Exception {
		when(makeService.getAllMakes()).thenReturn(Arrays.asList(make));

		mockMvc.perform(get("/api/v1/manufacturers").with(jwt())) // Mock JWT authentication
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].makeId").value(make.getMakeId()))
				.andExpect(jsonPath("$[0].makeName").value(make.getMakeName()));
	}
}