package de.eikeb.jcsv.reader.internal;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import de.eikeb.jcsv.reader.CSVEntryParser;

public class DefaultCSVEntryParserTest {

	@Test
	public void testParseEntry() {
		CSVEntryParser<String[]> parser = new DefaultCSVEntryParser();
		String[] data = {"A", "B", "C"};

		assertArrayEquals(data, parser.parseEntry(data));
	}

}
