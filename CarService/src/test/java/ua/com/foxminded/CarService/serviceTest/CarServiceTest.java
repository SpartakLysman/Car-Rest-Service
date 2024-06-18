package ua.com.foxminded.CarService.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.repository.CarRepository;
import ua.com.foxminded.carService.service.CarService;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

	@Mock
	private CarRepository carRepository;

	@InjectMocks
	private CarService carService;

	private Car testCar;

	@BeforeEach
	void setUp() {
		Make make = new Make(1L, "Toyota");
		Category category = new Category(1L, "SUV");
		List<Category> categories = new ArrayList<>();
		categories.add(category);

		testCar = new Car(1L, "99klWfTOAe", "Corolla", 2001, make, categories);
	}

	@Test
	void saveCarTest() {
		when(carRepository.save(any(Car.class))).thenReturn(testCar);

		Car savedCar = carService.saveCar(testCar);

		assertThat(savedCar).isNotNull();
		assertThat(savedCar.getObjectId()).isEqualTo(testCar.getObjectId());
		assertThat(savedCar.getModel()).isEqualTo(testCar.getModel());
		assertThat(savedCar.getYear()).isEqualTo(testCar.getYear());
		assertThat(savedCar.getMake()).isEqualTo(testCar.getMake());
		assertThat(savedCar.getCategories()).isEqualTo(testCar.getCategories());

		verify(carRepository, times(1)).save(any(Car.class));
	}

	@Test
	void updateCarTest() {
		when(carRepository.findById(1L)).thenReturn(Optional.of(testCar));
		when(carRepository.save(any(Car.class))).thenReturn(testCar);

		Car updatedCar = new Car();
		updatedCar.setModel("Camry");
		updatedCar.setYear(2005);

		Car result = carService.updateCar(1L, updatedCar);

		assertThat(result).isNotNull();
		assertThat(result.getModel()).isEqualTo(updatedCar.getModel());
		assertThat(result.getYear()).isEqualTo(updatedCar.getYear());

		verify(carRepository, times(1)).findById(1L);
		verify(carRepository, times(1)).save(any(Car.class));
	}

	@Test
	void deleteCarTest() {
		when(carRepository.findById(1L)).thenReturn(Optional.of(testCar));

		carService.deleteCar(1L);

		verify(carRepository, times(1)).findById(1L);
		verify(carRepository, times(1)).delete(any(Car.class));
	}

	@Test
	void getCarTest() {
		when(carRepository.findById(1L)).thenReturn(Optional.of(testCar));

		Optional<Car> result = carService.getCar(1L);

		assertThat(result).isPresent();
		assertThat(result.get()).isEqualTo(testCar);

		verify(carRepository, times(1)).findById(1L);
	}

	@Test
	void getCarsTest() {
		Pageable pageable = Pageable.unpaged();
		List<Car> carList = List.of(testCar);
		Page<Car> carPage = new PageImpl<>(carList);
		when(carRepository.findAll(pageable)).thenReturn(carPage);

		Page<Car> result = carService.getCars(pageable);

		assertThat(result).isNotNull();
		assertThat(result.getContent()).isEqualTo(carList);

		verify(carRepository, times(1)).findAll(pageable);
	}

	@Test
	void searchCarsTest() {
		Pageable pageable = Pageable.unpaged();
		List<Car> carList = List.of(testCar);
		Page<Car> carPage = new PageImpl<>(carList);
		when(carRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(carPage);

		Page<Car> result = carService.searchCars("Toyota", "Corolla", 2000, 2020, "SUV", pageable);

		assertThat(result).isNotNull();
		assertThat(result.getContent()).isEqualTo(carList);

		verify(carRepository, times(1)).findAll(any(Specification.class), eq(pageable));
	}
}
