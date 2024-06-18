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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ua.com.foxminded.carService.controller.MakeController;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.MakeService;

@WebMvcTest(MakeController.class)
public class MakeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MakeService makeService;

	@InjectMocks
	private MakeController makeController;

	private Make make1;
	private Make make2;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		make1 = new Make(1L, "Toyota");
		make2 = new Make(2L, "Tesla");
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void testCreateMake() throws Exception {
		when(makeService.saveMake(any(Make.class))).thenReturn(make1);

		mockMvc.perform(post("/api/v1/manufacturers").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(make1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.makeId").value(make1.getMakeId()))
				.andExpect(jsonPath("$.makeName").value(make1.getMakeName()));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void testUpdateMake() throws Exception {
		when(makeService.updateMake(anyLong(), any(Make.class))).thenReturn(make1);

		mockMvc.perform(put("/api/v1/manufacturers/1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(make1))).andExpect(status().isOk())
				.andExpect(jsonPath("$.makeId").value(make1.getMakeId()))
				.andExpect(jsonPath("$.makeName").value(make1.getMakeName()));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void testDeleteMake() throws Exception {
		mockMvc.perform(delete("/api/v1/manufacturers/1")).andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void testGetMake() throws Exception {
		when(makeService.getMake(anyLong())).thenReturn(Optional.of(make1));

		mockMvc.perform(get("/api/v1/manufacturers/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.makeId").value(make1.getMakeId()))
				.andExpect(jsonPath("$.makeName").value(make1.getMakeName()));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	public void testGetAllMakes() throws Exception {
		List<Make> makes = Arrays.asList(make1, make2);
		when(makeService.getAllMakes()).thenReturn(makes);

		mockMvc.perform(get("/api/v1/manufacturers")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].makeId").value(make1.getMakeId()))
				.andExpect(jsonPath("$[0].makeName").value(make1.getMakeName()))
				.andExpect(jsonPath("$[1].makeId").value(make2.getMakeId()))
				.andExpect(jsonPath("$[1].makeName").value(make2.getMakeName()));
	}
}
