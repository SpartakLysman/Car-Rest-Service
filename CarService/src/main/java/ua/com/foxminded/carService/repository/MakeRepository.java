package ua.com.foxminded.carService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.carService.model.Make;

public interface MakeRepository extends JpaRepository<Make, Long> {

	Optional<Make> findByMakeName(String makeName);
}
