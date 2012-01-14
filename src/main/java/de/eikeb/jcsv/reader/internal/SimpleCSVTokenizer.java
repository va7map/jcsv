package de.eikeb.jcsv.reader.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import de.eikeb.jcsv.CSVStrategy;
import de.eikeb.jcsv.reader.CSVTokenizer;

/**
 * A very simple csv tokenizer implementation.
 * If you do not need field quotations or multi line columns, this
 * will serve your purposes.
 *
 */
public class SimpleCSVTokenizer implements CSVTokenizer {

	/**
	 * Performs a split() on the input string. Uses the delimiter specified in the csv strategy.
	 *
	 */
	@Override
	public List<String> tokenizeLine(String line, CSVStrategy strategy, BufferedReader reader) throws IOException {
		return Arrays.asList(line.split(Pattern.quote(String.valueOf(strategy.getDelimiter()))));
	}
}
