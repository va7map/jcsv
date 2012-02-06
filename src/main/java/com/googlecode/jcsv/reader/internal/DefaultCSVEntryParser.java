package com.googlecode.jcsv.reader.internal;

import com.googlecode.jcsv.reader.CSVEntryParser;

/**
 * A default implementation of the CSVEntryParser.
 * This entry parser just returns the String[] array that it received.
 */
public class DefaultCSVEntryParser implements CSVEntryParser<String[]> {

	/**
	 * returns the input...
	 */
	@Override
	public String[] parseEntry(String... data) {
		return data;
	}
}
