package ua.com.foxminded.CarService.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.repository.MakeRepository;
import ua.com.foxminded.carService.service.MakeService;

@ExtendWith(MockitoExtension.class)
public class MakeServiceTest {

	@Mock
	private MakeRepository makeRepository;

	@InjectMocks
	private MakeService makeService;

	private Make testMake;

	@BeforeEach
	void setUp() {
		testMake = new Make(1L, "Toyota");
	}

	@Test
	void saveMakeTest() {
		when(makeRepository.save(any(Make.class))).thenReturn(testMake);

		Make savedMake = makeService.saveMake(testMake);

		assertThat(savedMake).isNotNull();
		assertThat(savedMake.getMakeId()).isEqualTo(testMake.getMakeId());
		assertThat(savedMake.getMakeName()).isEqualTo(testMake.getMakeName());

		verify(makeRepository, times(1)).save(any(Make.class));
	}

	@Test
	void updateMakeTest() {
		when(makeRepository.findById(1L)).thenReturn(Optional.of(testMake));
		when(makeRepository.save(any(Make.class))).thenReturn(testMake);

		Make updatedMake = new Make();
		updatedMake.setMakeName("Honda");

		Make result = makeService.updateMake(1L, updatedMake);

		assertThat(result).isNotNull();
		assertThat(result.getMakeName()).isEqualTo(updatedMake.getMakeName());

		verify(makeRepository, times(1)).findById(1L);
		verify(makeRepository, times(1)).save(any(Make.class));
	}

	@Test
	void deleteMakeTest() {
		when(makeRepository.findById(1L)).thenReturn(Optional.of(testMake));

		makeService.deleteMake(1L);

		verify(makeRepository, times(1)).findById(1L);
		verify(makeRepository, times(1)).delete(any(Make.class));
	}

	@Test
	void getMakeTest() {
		when(makeRepository.findById(1L)).thenReturn(Optional.of(testMake));

		Optional<Make> result = makeService.getMake(1L);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testMake);

		verify(makeRepository, times(1)).findById(1L);
	}

	@Test
	void getAllMakesTest() {
		List<Make> makes = new ArrayList<>();
		makes.add(testMake);
		when(makeRepository.findAll()).thenReturn(makes);

		List<Make> result = makeService.getAllMakes();

		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isEqualTo(testMake);

		verify(makeRepository, times(1)).findAll();
	}

	@Test
	void findByNameTest() {
		when(makeRepository.findByMakeName("Toyota")).thenReturn(Optional.of(testMake));

		Make result = makeService.findByName("Toyota");

		assertThat(result).isNotNull();
		assertThat(result).isEqualTo(testMake);

		verify(makeRepository, times(1)).findByMakeName("Toyota");
	}
}