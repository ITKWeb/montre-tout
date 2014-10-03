package fr.itkweb.hday.montretout.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.itkweb.hday.montretout.model.Person;

@Path("/persons")
public class PersonRestService {

	@GET
	@Path("/getDefaultPerson")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getDefaultPerson() {
		return Person.getDefaultPerson();
	}
}
