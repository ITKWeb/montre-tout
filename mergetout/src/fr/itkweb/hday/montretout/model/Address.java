package fr.itkweb.hday.montretout.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.Audited;

// Rest
@XmlRootElement
// Envers
@Entity
@Audited
@Table(name="Address")
public class Address {

	private static Address defaultAddress;

	public static Address getDefaultAddress() {
		if (defaultAddress == null) {
			defaultAddress = new Address("rue du voyageur galactique", 42);
		}
		return defaultAddress;
	}

	@Id
	@GeneratedValue
	private int id;

	@Column(name="streetName")
	private String streetName;

	@Column(name="houseNumber")
	private Integer houseNumber;

	@Column(name="flatNumber")
	private Integer flatNumber;

	@OneToMany(mappedBy = "address")
	private Set<Person> persons;


	// Constructors
	public Address() {
	}
	public Address(String streetName, int houseNumber) {
		this.streetName = streetName;
		this.houseNumber = houseNumber;
	}


	// Comparators
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Address)) {
			return false;
		}

		Address address = (Address) o;

		if (id != address.id) {
			return false;
		}
		if (flatNumber != null ? !flatNumber.equals(address.flatNumber) : address.flatNumber != null) {
			return false;
		}
		if (houseNumber != null ? !houseNumber.equals(address.houseNumber) : address.houseNumber != null) {
			return false;
		}
		if (streetName != null ? !streetName.equals(address.streetName) : address.streetName != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = id;
		result = 31 * result + (streetName != null ? streetName.hashCode() : 0);
		result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
		result = 31 * result + (flatNumber != null ? flatNumber.hashCode() : 0);
		return result;
	}


	// Getter and Setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(Integer flatNumber) {
		this.flatNumber = flatNumber;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}
}