package de.eikeb.jcsv.defaults;

import de.eikeb.jcsv.writer.CSVEntryConverter;

public class DefaultCSVEntryConverter implements CSVEntryConverter<String[]> {
	@Override
	public String[] convertEntry(String[] data) {
		return data;
	}
}
