package fr.itk.sample.envers;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Audited // that's the important part :)
@Table(name="Person")
public class Person {
    @Id
    @GeneratedValue
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;
    

    // Constructors
    public Person() {
	}
    
    public Person(String name, String surname, Address address) {
    	this.name = name;
    	this.surname = surname;
		this.address = address;
	}


	// Comparators
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (surname != null ? !surname.equals(person.surname) : person.surname != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}