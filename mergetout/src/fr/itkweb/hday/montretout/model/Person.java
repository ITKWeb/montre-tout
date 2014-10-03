package fr.itkweb.hday.montretout.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.envers.Audited;

// Rest
@XmlRootElement
// Envers
@Entity
@Audited
@Table(name="Person")
public class Person {

	private static Person defaultPerson;
	public static Person getDefaultPerson() {
		if (defaultPerson == null) {
			defaultPerson = new Person("Henry", "Enrirapa", Address.getDefaultAddress());
		}
		return defaultPerson;
	}

	@Id
	@GeneratedValue
	private int id;

	@Column(name="name")
	private String firstName;

	@Column(name="surname")
	private String lastName;

	@ManyToOne
	@JoinColumn(name="address_id")
	private Address address;


	// Constructors
	public Person() {
	}

	public Person(String firstName, String lastName, Address address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}


	// Comparators
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Person)) {
			return false;
		}

		Person person = (Person) o;

		if (id != person.id) {
			return false;
		}
		if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) {
			return false;
		}
		if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = id;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		return result;
	}


	// Getter and Setter methods
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return firstName;
	}

	public void setName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return lastName;
	}

	public void setSurname(String lastName) {
		this.lastName = lastName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}