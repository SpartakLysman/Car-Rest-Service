package ua.com.foxminded.carService.controller;

import java.util.Optional;

import org.springdoc.core.annotations.ParameterObject;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.service.CarService;

@RestController
@RequestMapping("/api/v1/cars")
@SecurityRequirement(name = "bearerAuth")
public class CarController {

	@Autowired
	private CarService carService;

	@Operation(summary = "Create a new car")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Car created successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping
	public ResponseEntity<Car> createCar(@RequestBody Car car) {
		Car createdCar = carService.saveCar(car);
		return ResponseEntity.ok(createdCar);
	}

	@Operation(summary = "Update an existing car")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Car updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<Car> updateCar(@Parameter(description = "ID of the car to be updated") @PathVariable Long id,
			@RequestBody Car carDetails) {
		Car updatedCar = carService.updateCar(id, carDetails);
		return ResponseEntity.ok(updatedCar);
	}

	@Operation(summary = "Delete a car by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Car deleted successfully", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCar(
			@Parameter(description = "ID of the car to be deleted") @PathVariable Long id) {
		carService.deleteCar(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Get a car by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Car retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Car.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<Car> getCar(@Parameter(description = "ID of the car to be retrieved") @PathVariable Long id) {
		Optional<Car> car = carService.getCar(id);
		return car.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "Get all cars with pagination")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cars retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@GetMapping
	public ResponseEntity<Page<Car>> getCars(@ParameterObject Pageable pageable) {
		Page<Car> cars = carService.getCars(pageable);
		return ResponseEntity.ok(cars);
	}

	@Operation(summary = "Search for cars by various criteria")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cars retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@GetMapping("/search")
	public ResponseEntity<Page<Car>> searchCars(@RequestParam(required = false) String manufacturer,
			@RequestParam(required = false) String model, @RequestParam(required = false) Integer minYear,
			@RequestParam(required = false) Integer maxYear, @RequestParam(required = false) String category,
			@ParameterObject Pageable pageable) {
		Page<Car> cars = carService.searchCars(manufacturer, model, minYear, maxYear, category, pageable);
		return ResponseEntity.ok(cars);
	}
}