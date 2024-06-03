package ua.com.foxminded.carService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.carService.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName);
}
