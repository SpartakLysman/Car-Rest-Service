package ua.com.foxminded.carService.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "car")
@jakarta.persistence.Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "objectId")
	private String id;

	@Column(name = "model")
	private String model;

	@Column(name = "year")
	private int year;

	@ManyToOne
	@JoinColumn(name = "makeId", nullable = false)
	private Make make;

	@ManyToMany
	@JoinTable(name = "car_category", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Category category;

	public Car(String id, String model, int year, Make make, Category category) {
		this.id = id;
		this.model = model;
		this.year = year;
		this.make = make;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	public Make getMake() {
		return make;
	}

	public Category getCategory() {
		return category;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setMake(Make make) {
		this.make = make;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String toString() {
		return "Id: " + id + ",  Model: " + model + ",  Year: " + year + ",  Make: " + make + ",  Category: "
				+ category;
	}
}