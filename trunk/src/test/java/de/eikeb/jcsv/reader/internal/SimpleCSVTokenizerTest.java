package de.eikeb.jcsv.reader.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import de.eikeb.jcsv.CSVStrategy;
import de.eikeb.jcsv.reader.CSVTokenizer;

public class SimpleCSVTokenizerTest extends TestCase {

	@Test
	@SuppressWarnings("serial")
	public void testTokenizeLine() throws IOException {
		CSVTokenizer tokenizer = new SimpleCSVTokenizer();

		// uses ; as delimiter
		final CSVStrategy strategy = CSVStrategy.DEFAULT;
		final String delimiter = String.valueOf(strategy.getDelimiter());

		// we only do simple tests
		String line = "A;B;C";
		List<String> token = tokenizer.tokenizeLine(line, strategy, null);
		List<String> expected = new ArrayList<String>() {{
			add("A"); add("B"); add("C");
		}};
		assertEquals(expected, token);

		line = "A";
		token = tokenizer.tokenizeLine(line, strategy, null);
		expected = new ArrayList<String>() {{
			add("A");
		}};
		assertEquals(expected, token);

		line = "A;";
		token = tokenizer.tokenizeLine(line, strategy, null);
		expected = new ArrayList<String>() {{
			add("A"); add("");
		}};
		assertEquals(expected, token);

		line = delimiter + delimiter;
		token = tokenizer.tokenizeLine(line, strategy, null);
		expected = new ArrayList<String>() {{
			add(""); add(""); add("");
		}};
		assertEquals(expected, token);

		line = "";
		token = tokenizer.tokenizeLine(line, strategy, null);
		expected = new ArrayList<String>();
		assertEquals(expected, token);
	}

}
