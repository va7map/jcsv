package de.eikeb.jcsv.writer.internal;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import de.eikeb.jcsv.writer.CSVEntryConverter;

public class DefaultCSVEntryConverterTest {

	@Test
	public void testConvertEntry() {
		CSVEntryConverter<String[]> converter = new DefaultCSVEntryConverter();
		String[] data = {"A", "B", "C"};

		assertArrayEquals(data, converter.convertEntry(data));
	}

}
