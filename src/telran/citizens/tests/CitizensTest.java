package telran.citizens.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.citizens.dao.Citizens;
import telran.citizens.intefaces.ICitizens;
import telran.citizens.model.Person;

class CitizensTest {
	ICitizens citizens;

	@BeforeEach
	void setUp() throws Exception {
		citizens = new Citizens(Arrays.asList(
				new Person(1, "Peter", "Jackson", 23),
				new Person(2, "John", "Smith", 20),
				new Person(3, "Mary", "Jackson", 20),
				new Person(4, "Tigran", "Petrosian", 25)));
	}

	@Test
	void testCitizensListOfPerson() {
		citizens = new Citizens(Arrays.asList(
				new Person(1, "Peter", "Jackson", 23),
				new Person(1, "Peter", "Jackson", 23),
				null));
		assertEquals(1, citizens.size());
	}

	@Test
	void testAdd() {
		assertFalse(citizens.add(null));
		assertFalse(citizens.add(new Person(2, "John", "Smith", 20)));
		assertEquals(4, citizens.size());
		assertTrue(citizens.add(new Person(5, "John", "Smith", 20)));
		assertEquals(5, citizens.size());
	}

	@Test
	void testRemove() {
		assertFalse(citizens.remove(5));
		assertEquals(4, citizens.size());
		assertTrue(citizens.remove(2));
		assertEquals(3, citizens.size());
	}

	@Test
	void testFindInt() {
		Person person = citizens.find(1);
		assertEquals(1, person.getId());
		assertEquals("Peter", person.getFirstName());
		assertEquals("Jackson", person.getLastName());
		assertEquals(23, person.getAge());
		assertNull(citizens.find(5));
	}

	@Test
	void testFindIntInt() {
		Iterable<Person> persons = citizens.find(20, 23);
		Person[] expected = {
				new Person(1, "Peter", "Jackson", 23),
				new Person(2, "John", "Smith", 20),
				new Person(3, "Mary", "Jackson", 20),
		};
		List<Person> tmp = new ArrayList<>();
		for (Person person : persons) {
			tmp.add(person);
		}
		Person[] actual = tmp.toArray(new Person[0]);
		Arrays.sort(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	void testFindString() {
		Iterable<Person> persons = citizens.find("Jackson");
		Person[] expected = {
				new Person(1, "Peter", "Jackson", 23),
				new Person(3, "Mary", "Jackson", 20),
		};
		List<Person> tmp = new ArrayList<>();
		for (Person person : persons) {
			tmp.add(person);
		}
		Person[] actual = tmp.toArray(new Person[0]);
		Arrays.sort(actual);
		assertArrayEquals(expected, actual);
	}

	@Test
	void testGetAllPersonSortedById() {
		Iterable<Person> persons = citizens.getAllPersonSortedById();
		int id = Integer.MIN_VALUE;
		for (Person person : persons) {
			assertTrue(person.getId() > id);
			id = person.getId();
		}
	}

	@Test
	void testGetAllPersonSortedByLastName() {
		Iterable<Person> persons = citizens.getAllPersonSortedByLastName();
		String lastName = "";
		for (Person person : persons) {
			assertTrue(person.getLastName().compareTo(lastName) >= 0);
			lastName = person.getLastName();
		}
	}

	@Test
	void testGetAllPersonSortedByAge() {
		Iterable<Person> persons = citizens.getAllPersonSortedByAge();
		int age = Integer.MIN_VALUE;
		for (Person person : persons) {
			assertTrue(person.getAge() >= age);
			age = person.getAge();
		}
	}

	@Test
	void testSize() {
		assertEquals(4, citizens.size());
	}

}
