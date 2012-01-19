package de.eikeb.jcsv;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSVUtilTest {

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
