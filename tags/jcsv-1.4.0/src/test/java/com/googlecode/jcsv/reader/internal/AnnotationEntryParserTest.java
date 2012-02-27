package com.googlecode.jcsv.reader.internal;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

import com.googlecode.jcsv.annotations.MapToColumn;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;

public class AnnotationEntryParserTest {
	
	@Test
	public void testParseEntry() throws ParseException {
		ValueProcessorProvider provider = new ValueProcessorProvider();
		CSVEntryParser<Person> entryParser = new AnnotationEntryParser<Person>(Person.class, provider);
		
		DateFormat df = DateFormat.getDateInstance();
		Person expected = new Person("Hans", "im Glück", 18, df.parse("12.12.2012"));
		
		String[] data = {"Hans", "im Glück", "18", "12.12.2012"};
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
		
		@MapToColumn(column=3)
		private Date birthday;
		
		public Person() {
		}
		
		public Person(String firstName, String lastName, int age, Date birthday) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.birthday = birthday;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
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
			if (birthday == null) {
				if (other.birthday != null)
					return false;
			} else if (!birthday.equals(other.birthday))
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
