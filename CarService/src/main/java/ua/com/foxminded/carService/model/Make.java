package ua.com.foxminded.carService.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "make")
@jakarta.persistence.Entity
public class Make {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "makeId")
	private Long makeId;

	@Column(name = "makeName")
	private String makeName;

	@OneToMany(mappedBy = "make")
	private List<Car> cars;

	public Make(long makeId, String makeName) {
		this.makeId = makeId;
		this.makeName = makeName;
	}

	public Make(String makeName) {
		this.makeName = makeName;
	}

	public Make() {

	}

	public Long getMakeId() {
		return makeId;
	}

	public void setMakeId(Long makeId) {
		this.makeId = makeId;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public List<Car> getCars() {
		return cars;
	}

	public void setCars(List<Car> cars) {
		this.cars = cars;
	}

	public String toString() {
		return "Make id: " + makeId + ",  Make name: " + makeName;
	}
}
