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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.service.MakeService;

@RestController
@RequestMapping("/api/v1/manufacturers")
@SecurityRequirement(name = "bearerAuth")
public class MakeController {

	@Autowired
	private MakeService makeService;

	@Operation(summary = "Create a new make")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Make created successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Make.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@PostMapping
	public ResponseEntity<Make> createMake(@RequestBody Make make) {
		Make createdMake = makeService.saveMake(make);
		return ResponseEntity.ok(createdMake);
	}

	@Operation(summary = "Update an existing make")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Make updated successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Make.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Make not found", content = @Content) })
	@PutMapping("/{id}")
	public ResponseEntity<Make> updateMake(
			@Parameter(description = "ID of the make to be updated") @PathVariable Long id,
			@RequestBody Make makeDetails) {
		Make updatedMake = makeService.updateMake(id, makeDetails);
		return ResponseEntity.ok(updatedMake);
	}

	@Operation(summary = "Delete a make by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Make deleted successfully", content = @Content),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Make not found", content = @Content) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMake(
			@Parameter(description = "ID of the make to be deleted") @PathVariable Long id) {
		makeService.deleteMake(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Get a make by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Make retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Make.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
			@ApiResponse(responseCode = "404", description = "Make not found", content = @Content) })
	@GetMapping("/{id}")
	public ResponseEntity<Make> getMake(
			@Parameter(description = "ID of the make to be retrieved") @PathVariable Long id) {
		Optional<Make> make = makeService.getMake(id);
		return make.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Operation(summary = "Get all makes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Makes retrieved successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = List.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content) })
	@GetMapping
	public ResponseEntity<List<Make>> getAllMakes() {
		List<Make> makes = makeService.getAllMakes();
		return ResponseEntity.ok(makes);
	}
}
