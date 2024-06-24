package ua.com.foxminded.carService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.com.foxminded.carService.exception.ResourceNotFoundException;
import ua.com.foxminded.carService.model.Make;
import ua.com.foxminded.carService.repository.MakeRepository;

@Service
public class MakeService {

	@Autowired
	private MakeRepository makeRepository;

	public Make saveMake(Make make) {
		return makeRepository.save(make);
	}

	public Make updateMake(Long id, Make makeDetails) {
		Make make = makeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Make not found with id " + id));

		make.setMakeName(makeDetails.getMakeName());

		return makeRepository.save(make);
	}

	public void deleteMake(Long id) {
		Make make = makeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Make not found with id " + id));
		makeRepository.delete(make);
	}

	public Make findByName(String name) {
		return makeRepository.findByMakeName(name)
				.orElseThrow(() -> new ResourceNotFoundException("Make not found with name: " + name));
	}

	public Optional<Make> getMake(Long id) {
		return makeRepository.findById(id);
	}

	public List<Make> getAllMakes() {
		return makeRepository.findAll();
	}
}
