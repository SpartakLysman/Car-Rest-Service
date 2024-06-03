package ua.com.foxminded.carService.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

	@Autowired
	private CarService carService;

	@PostMapping
	public ResponseEntity<Car> createCar(@RequestBody Car car) {
		Car createdCar = carService.saveCar(car);
		return ResponseEntity.ok(createdCar);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
		Car updatedCar = carService.updateCar(id, carDetails);
		return ResponseEntity.ok(updatedCar);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
		carService.deleteCar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Car> getCar(@PathVariable Long id) {
		Optional<Car> car = carService.getCar(id);
		return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<Page<Car>> getCars(Pageable pageable) {
		Page<Car> cars = carService.getCars(pageable);
		return ResponseEntity.ok(cars);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<Car>> searchCars(@RequestParam(required = false) String manufacturer,
			@RequestParam(required = false) String model, @RequestParam(required = false) Integer minYear,
			@RequestParam(required = false) Integer maxYear, @RequestParam(required = false) String category,
			Pageable pageable) {
		Page<Car> cars = carService.searchCars(manufacturer, model, minYear, maxYear, category, pageable);
		return ResponseEntity.ok(cars);
	}
}
