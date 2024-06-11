package ua.com.foxminded.carService.model;

import java.util.ArrayList;
import java.util.List;

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
	@Column(name = "id")
	private long id;

	@Column(name = "objectId")
	private String objectId;

	@Column(name = "model")
	private String model;

	@Column(name = "year")
	private int year;

	@ManyToOne
	@JoinColumn(name = "makeId", nullable = false)
	private Make make;

	@ManyToMany
	@JoinTable(name = "car_category", joinColumns = @JoinColumn(name = "car_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories = new ArrayList<>();

	public Car(long id, String objectId, String model, int year, Make make, List<Category> categories) {
		this.id = id;
		this.objectId = objectId;
		this.model = model;
		this.year = year;
		this.make = make;
		this.categories = new ArrayList<>();
	}

	public Car(long id, String objectId, String model, int year, Make make) {
		this.id = id;
		this.objectId = objectId;
		this.model = model;
		this.year = year;
		this.make = make;
	}

	public Car(String objectId, String model, int year, Make make, List<Category> categories) {
		this.objectId = objectId;
		this.model = model;
		this.year = year;
		this.make = make;
		this.categories = new ArrayList<>();
	}

	public Car(String model, int year, Make make, List<Category> categories) {
		this.model = model;
		this.year = year;
		this.make = make;
		this.categories = new ArrayList<>();
	}

	public Car() {

	}

	public long getId() {
		return id;
	}

	public String getObjectId() {
		return objectId;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
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

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String toString() {
		return "Id: " + id + ", Object id: " + objectId + ",  Model: " + model + ",  Year: " + year + ",  Make: " + make
				+ ",  Categories: " + categories;
	}
}