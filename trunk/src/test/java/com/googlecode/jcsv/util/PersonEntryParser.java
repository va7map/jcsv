package com.googlecode.jcsv.util;

import com.googlecode.jcsv.reader.CSVEntryParser;

public class PersonEntryParser implements CSVEntryParser<Person> {
	@Override
	public Person parseEntry(String... data) {
		String firstName = data[0];
		String lastName = data[1];
		int age = Integer.parseInt(data[2]);

		return new Person(firstName, lastName, age);
	}
}