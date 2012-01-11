package de.eikeb.jcsv.defaults;

import de.eikeb.jcsv.reader.CSVEntryParser;

/**
 * A default implementation of the CSVEntryParser.
 * This entry parser just returns the String[] array that it received.
 */
public class DefaultCSVEntryParser implements CSVEntryParser<String[]> {

	/**
	 * returns the input...
	 */
	public String[] parseEntry(String... data) {
		return data;
	}
}
