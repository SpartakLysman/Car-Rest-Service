package ua.com.foxminded.carService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;
import ua.com.foxminded.carService.service.CategoryService;
import ua.com.foxminded.carService.service.MakeService;

@RestController
@RequestMapping("/api/v1/manufacturers")
@SecurityRequirement(name = "bearerAuth")
public class ManufacturerModelController {

	@Autowired
	private MakeService makeService;

	@Autowired
	private CarService carService;

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/{manufacturer}/models/{model}/{year}")
	@Operation(summary = "Create a car with the specified manufacturer, model, and year", description = "Creates a car with the given manufacturer, model, year, and categories.", security = {
			@SecurityRequirement(name = "bearerAuth") })
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Car created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "404", description = "Manufacturer or category not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<Car> createCarWithManufacturerModelAndYear(
			@Parameter(description = "Manufacturer name", required = true) @PathVariable String manufacturer,
			@Parameter(description = "Model name", required = true) @PathVariable String model,
			@Parameter(description = "Year of the car", required = true) @PathVariable int year,
			@RequestBody List<String> categories) {

		Make make = makeService.findByName(manufacturer);
		if (make == null) {
			make = new Make();
			make.setMakeName(manufacturer);
			make = makeService.saveMake(make);
		}

		Car car = new Car();
		car.setMake(make);
		car.setModel(model);
		car.setYear(year);

		List<Category> carCategories = new ArrayList<>();
		for (String categoryName : categories) {
			Category category = categoryService.findByName(categoryName);
			if (category == null) {
				category = new Category();
				category.setCategoryName(categoryName);
				category = categoryService.saveCategory(category);
			}
			carCategories.add(category);
		}
		car.setCategories(carCategories);

		Car createdCar = carService.saveCar(car);
		return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
	}
}
