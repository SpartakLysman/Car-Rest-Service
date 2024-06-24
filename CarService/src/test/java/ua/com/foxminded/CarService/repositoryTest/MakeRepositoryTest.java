package ua.com.foxminded.CarService.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.repository.MakeRepository;

@DataJpaTest
public class MakeRepositoryTest {

	@Autowired
	private MakeRepository makeRepository;

	@BeforeEach
	void setUp() {
		makeRepository.deleteAll();
	}

	@Test
	void findByMakeName_WhenMakeExists_ReturnMake() {
		Make toyota = new Make("Toyota");
		makeRepository.save(toyota);

		Optional<Make> foundMake = makeRepository.findByMakeName("Toyota");

		assertThat(foundMake).isPresent();
		assertThat(foundMake.get().getMakeName()).isEqualTo(toyota.getMakeName());
	}

	@Test
	void findByMakeName_WhenMakeDoesNotExist_ReturnEmptyOptional() {
		Optional<Make> foundMake = makeRepository.findByMakeName("NonExistingMake");

		assertThat(foundMake).isNotPresent();
	}
}
