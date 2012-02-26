package com.googlecode.jcsv.reader.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.googlecode.jcsv.annotations.MapToColumn;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;

public class AnnotationEntryParserTest {
	
	@Test
	public void testParseEntry() {
		ValueProcessorProvider provider = new ValueProcessorProvider();
		CSVEntryParser<Person> entryParser = new AnnotationEntryParser<Person>(Person.class, provider);
		
		Person expected = new Person("Hans", "im Glück", 18);
		String[] data = {"Hans", "im Glück", "18"};
		Person result = entryParser.parseEntry(data);
		assertEquals(expected, result);
	}

	private static class Person {
		@MapToColumn(column=0)
		private String firstName;
		
		@MapToColumn(column=1)
		private String lastName;
		
		@MapToColumn(column=2)
		private int age;
		
		public Person() {
		}
		
		public Person(String firstName, String lastName, int age) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
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
	}
}
