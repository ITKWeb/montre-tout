import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import fr.itk.sample.envers.Address;
import fr.itk.sample.envers.Person;
import fr.itk.utils.HibernateUtil;

@SuppressWarnings("unused")
public class Envers {

	private static Address address1;
	private static Address address2;

	private static Person person1;
	private static Person person2;

	public static void main(String[] args) {
		/*
		 * DOC: http://docs.jboss.org/envers/docs/ SVN:
		 * http://anonsvn.jboss.org/repos/hibernate/core/trunk/envers/src/main/
		 */

		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.openSession();
		AuditReader reader = AuditReaderFactory.get(session);

		// populate(session);
		// change(session);
		retrieve(session);
		oldVersion(session);
		// testVersion(session, reader);
		listVersions(session, reader);
		nowVersions(session, reader);

		session.close();
		System.exit(0);
	}

	private static void nowVersions(Session session, AuditReader reader) {
		System.out.println();
		System.out.println("---- now versions ----");
		session.getTransaction().begin();

		Number lastVersion = reader.getRevisionNumberForDate(new Date(System.currentTimeMillis()));

		for (Person person : new Person[] { person1, person2 }) {
			Person p = reader.find(Person.class, person.getId(), lastVersion.intValue());
			System.out.println("[i] v" + lastVersion + ": " + p.getName() + " " + p.getSurname() + " @ " + p.getAddress().getHouseNumber()
					+ ", " + p.getAddress().getStreetName());
		}

		session.getTransaction().commit();
	}

	private static void listVersions(Session session, AuditReader reader) {
		System.out.println();
		System.out.println("---- list versions ----");
		session.getTransaction().begin();

		for (Person person : new Person[] { person1, person2 }) {
			for (Number revision : reader.getRevisions(Person.class, person.getId())) {
				Person p = reader.find(Person.class, person.getId(), revision.intValue());
				System.out.println("[i] v" + revision + ": " + p.getName() + " " + p.getSurname() + " @ " + p.getAddress().getHouseNumber()
						+ ", " + p.getAddress().getStreetName());
			}
		}

		session.getTransaction().commit();
	}

	private static void testVersion(Session session, AuditReader reader) {
		System.out.println();
		System.out.println("---- test version ----");
		session.getTransaction().begin();

		Object lastAddressRevision = reader.getCurrentRevision(Address.class, false);
		Object lastPersonRevision = reader.getCurrentRevision(Person.class, false);

		Object lastPersistAddressRevision = reader.getCurrentRevision(Address.class, true);
		Object lastPersistPersonRevision = reader.getCurrentRevision(Person.class, true);

		System.out.println("[i] Address: last version: ");
		System.out.println("[i] Person: last version: ");

		session.getTransaction().commit();
	}

	private static void populate(Session session) {
		System.out.println();
		System.out.println("---- populate ----");
		session.getTransaction().begin();

		address1 = new Address("Privet Drive", 4);
		person1 = new Person("Harry", "Potter", address1);

		address2 = new Address("Grimmauld Place", 12);
		person2 = new Person("Hermione", "Granger", address2);

		session.persist(address1);
		session.persist(address2);
		session.persist(person1);
		session.persist(person2);

		System.out.println("[n] Harry Potter @ 4, Privet Drive");
		System.out.println("[n] Hermione Granger @ 12, Grimmauld Place");

		session.getTransaction().commit();
	}

	private static void retrieve(Session session) {
		System.out.println();
		System.out.println("---- in database ----");
		session.getTransaction().begin();

		address1 = (Address) session.get(Address.class, 1);
		address2 = (Address) session.get(Address.class, 2);

		for (Address a : new Address[] { address1, address2 }) {
			System.out.println("[i] adrs " + a.getId() + ": " + a.getHouseNumber() + ", " + a.getStreetName());
		}

		person1 = (Person) session.get(Person.class, 1);
		person2 = (Person) session.get(Person.class, 2);

		for (Person p : new Person[] { person1, person2 }) {
			System.out.println("[i] pers " + p.getId() + ": " + p.getName() + " " + p.getSurname() + " @ " + p.getAddress().getId() + ": "
					+ p.getAddress().getHouseNumber() + ", " + p.getAddress().getStreetName());
		}

		session.getTransaction().commit();
	}

	private static void change(Session session) {
		System.out.println();
		System.out.println("---- change ----");
		session.getTransaction().begin();

		address1 = (Address) session.get(Address.class, address1.getId());
		person2 = (Person) session.get(Person.class, person2.getId());

		// Changing the address's house number
		address1.setHouseNumber(5);

		// And moving Hermione to Harry
		person2.setAddress(address1);

		System.out.println("[u] Harry Potter @ ==>5<==, Privet Drive");
		System.out.println("[u] Hermione Granger @ ==>5, Privet Drive<==");

		session.getTransaction().commit();
	}

	private static void oldVersion(Session session) {
		System.out.println();
		System.out.println("---- old version ----");
		AuditReader reader = AuditReaderFactory.get(session);

		Person person2_rev1 = reader.find(Person.class, person2.getId(), 1);
		System.out.println(
				"[t] version 1: Hermione Granger @ 12, Grimmauld Place : " +
						String.valueOf(person2_rev1.getAddress().equals(new Address("Grimmauld Place", 12))));

		Address address1_rev1 = reader.find(Address.class, address1.getId(), 1);
		System.out.println(
				"[t] version 1: <?> @ 12, Grimmauld Place : " +
						String.valueOf(address1_rev1.getPersons().size()));
	}
}
