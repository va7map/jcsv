package de.eikeb.jcsv.reader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSVReaderTest extends TestCase {

	private static final String NEW_LINE = System.getProperty("line.separator");

	private CSVReader<Person> csvReader;

	@Override
	@Before
	public void setUp() throws Exception {
		String input = "Hans;\"im \"\"Glück\"\"\";16" + NEW_LINE
				+ "Klaus;Meyer;33" + NEW_LINE;
		StringReader sr = new StringReader(input);

		csvReader = new CSVReader.Builder<Person>(sr).entryParser(new PersonEntyParser()).build();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		csvReader.close();
	}

	@Test
	@SuppressWarnings("serial")
	public void testReadAll() throws IOException {
		List<Person> result = csvReader.readAll();
		List<Person> expected = new ArrayList<Person>() {{
			add(new Person("Hans", "im \"Glück\"", 16));
			add(new Person("Klaus", "Meyer", 33));
		}};
		System.out.println(result);
		assertEquals(expected, result);
	}

	@Test
	public void testReadNext() throws IOException {
		Person result = csvReader.readNext();
		Person expected = new Person("Hans", "im \"Glück\"", 16);
		assertEquals(expected, result);

		result = csvReader.readNext();
		expected = new Person("Klaus", "Meyer", 33);
		assertEquals(expected, result);
	}

	private static class PersonEntyParser implements CSVEntryParser<Person> {
		@Override
		public Person parseEntry(String... data) {
			String firstName = data[0];
			String lastName = data[1];
			int age = Integer.parseInt(data[2]);

			return new Person(firstName, lastName, age);
		}
	}

	private static class Person {
		private final String firstName;
		private final String lastName;
		private final int age;

		public Person(String firstName, String lastName, int age) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
			result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			if (age != other.age)
				return false;
			if (firstName == null) {
				if (other.firstName != null)
					return false;
			} else if (!firstName.equals(other.firstName))
				return false;
			if (lastName == null) {
				if (other.lastName != null)
					return false;
			} else if (!lastName.equals(other.lastName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Person[" + firstName + " " + lastName + " " + age + "]";
		}
	}
}
