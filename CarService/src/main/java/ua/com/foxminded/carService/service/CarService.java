package ua.com.foxminded.carService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import ua.com.foxminded.carService.exception.ResourceNotFoundException;
import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.repository.CarRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	public Car saveCar(Car car) {
		return carRepository.save(car);
	}

	public Car updateCar(Long id, Car carDetails) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Car not found with id " + id));

		car.setYear(carDetails.getYear());
		car.setModel(carDetails.getModel());
		car.setMake(carDetails.getMake());
		car.setCategories(carDetails.getCategories());

		return carRepository.save(car);
	}

	public void deleteCar(Long id) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Car not found with id " + id));
		carRepository.delete(car);
	}

	public Optional<Car> getCar(Long id) {
		return carRepository.findById(id);
	}

	public Page<Car> getCars(Pageable pageable) {
		return carRepository.findAll(pageable);
	}

	public Page<Car> searchCars(String manufacturer, String model, Integer minYear, Integer maxYear, String category,
			Pageable pageable) {
		return carRepository.findAll((root, query, criteriaBuilder) -> {
			Predicate predicate = criteriaBuilder.conjunction();

			if (manufacturer != null) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.equal(root.get("make").get("makeName"), manufacturer));
			}

			if (model != null) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("model"), model));
			}

			if (minYear != null) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear));
			}

			if (maxYear != null) {
				predicate = criteriaBuilder.and(predicate,
						criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear));
			}

			if (category != null) {
				predicate = criteriaBuilder.and(predicate, criteriaBuilder.isMember(category, root.get("categories")));
			}

			return predicate;
		}, pageable);
	}
}