package ua.com.foxminded.CarService.repositoryTest;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class MakeRepositoryTest {
//
//	@Autowired
//	private MakeRepository makeRepository;
//
//	@BeforeEach
//	void setUp() {
//		makeRepository.deleteAll();
//	}
//
//	@Test
//	void findByMakeName_WhenMakeExists_ReturnMake() {
//		Make toyota = new Make("Toyota");
//		makeRepository.save(toyota);
//
//		Optional<Make> foundMake = makeRepository.findByMakeName("Toyota");
//
//		assertThat(foundMake).isPresent();
//		assertThat(foundMake.get().getMakeName()).isEqualTo(toyota.getMakeName());
//	}
//
//	@Test
//	void findByMakeName_WhenMakeDoesNotExist_ReturnEmptyOptional() {
//		Optional<Make> foundMake = makeRepository.findByMakeName("NonExistingMake");
//
//		assertThat(foundMake).isNotPresent();
//	}
}
