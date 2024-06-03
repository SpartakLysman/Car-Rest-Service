package ua.com.foxminded.carService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.MakeService;

@RestController
@RequestMapping("/api/v1/manufacturers")
public class MakeController {

	@Autowired
	private MakeService makeService;

	@PostMapping
	public ResponseEntity<Make> createMake(@RequestBody Make make) {
		Make createdMake = makeService.saveMake(make);
		return ResponseEntity.ok(createdMake);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Make> updateMake(@PathVariable Long id, @RequestBody Make makeDetails) {
		Make updatedMake = makeService.updateMake(id, makeDetails);
		return ResponseEntity.ok(updatedMake);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMake(@PathVariable Long id) {
		makeService.deleteMake(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Make> getMake(@PathVariable Long id) {
		Optional<Make> make = makeService.getMake(id);
		return make.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping
	public ResponseEntity<List<Make>> getAllMakes() {
		List<Make> makes = makeService.getAllMakes();
		return ResponseEntity.ok(makes);
	}
}
