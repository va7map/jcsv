package de.eikeb.jcsv;

import junit.framework.TestCase;

import org.junit.Test;

public class CSVUtilTest extends TestCase {

	@Test
	public void testImplode() {
		String[] data = {"A", "B", "C"};
		String joined = CSVUtil.implode(data, ";");
		assertEquals(joined, "A;B;C");

		String[] data2 = {"A"};
		joined = CSVUtil.implode(data2, ";");
		assertEquals(joined, "A");

		String[] data3 = new String[0];
		joined = CSVUtil.implode(data3, ";");
		assertEquals(joined, "");
	}

}
