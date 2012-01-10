package de.eikeb.csvparser.defaults;

import de.eikeb.csvparser.CSVEntryParser;

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
