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

import ua.com.foxminded.carService.model.Car;
import ua.com.foxminded.carService.model.Category;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.CarService;
import ua.com.foxminded.carService.service.CategoryService;
import ua.com.foxminded.carService.service.MakeService;

@RestController
@RequestMapping("/api/v1/manufacturers")
public class ManufacturerModelController {

	@Autowired
	private MakeService makeService;

	@Autowired
	private CarService carService;

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/{manufacturer}/models/{model}/{year}")
	public ResponseEntity<Car> createCarWithManufacturerModelAndYear(@PathVariable String manufacturer,
			@PathVariable String model, @PathVariable int year, @RequestBody List<String> categories) {

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
