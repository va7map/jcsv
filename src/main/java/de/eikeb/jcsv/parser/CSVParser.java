package de.eikeb.jcsv.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import de.eikeb.jcsv.CSVStrategy;
import de.eikeb.jcsv.defaults.DefaultCSVEntryParser;

public class CSVParser<E> {
	private final Reader reader;
	private final CSVStrategy strategy;
	private final CSVEntryParser<E> entryParser;

	private CSVParser(Builder<E> builder) {
		this.reader = builder.reader;
		this.strategy = builder.strategy;
		this.entryParser = builder.entryParser;
	}

	/**
	 * Returns a default configured CSVParser<String[]>.
	 * It uses the DefaultCSVEntryParser that allows you to
	 * convert a csv file into a List<String[]>.
	 *
	 * @param input the csv reader
	 * @return the CSVParser
	 */
	public static CSVParser<String[]> newDefaultParser(Reader input) {
		return new Builder<String[]>(input).entryParser(new DefaultCSVEntryParser()).build();
	}

	/**
	 * Parses the csv file and returns a List of created objects.
	 *
	 * @return List of E
	 * @throws IOException
	 */
	public List<E> parse() throws IOException {
		List<E> entries = new ArrayList<E>();
		String delimiterPattern = Pattern.quote(String.valueOf(strategy.getDelimiter()));

		BufferedReader br = new BufferedReader(reader);

		if (strategy.isSkipHeader()) {
			// reads the first line to skip the header
			br.readLine();
		}

		String line;
		while ((line = br.readLine()) != null) {
			if (isCommentLine(line)) {
				continue;
			}

			String[] data = line.split(delimiterPattern);

			E entry = entryParser.parseEntry(data);
			entries.add(entry);
		}

		return entries;
	}

	private boolean isCommentLine(String line) {
		return line.startsWith(String.valueOf(strategy.getCommentStart()));
	}

	/**
	 * The Builder that creates the CSVParser objects.
	 *
	 * @param <E> The Type that your rows represent
	 */
	public static class Builder<E> {
		private final Reader reader;
		private CSVEntryParser<E> entryParser;
		private CSVStrategy strategy = CSVStrategy.DEFAULT;

		/**
		 * @param reader the csv reader
		 */
		public Builder(Reader reader) {
			this.reader = reader;
		}

		/**
		 * Sets the strategy that the CSVParser will use.
		 *
		 * @param strategy the csv strategy
		 * @return this builder
		 */
		public Builder<E> strategy(CSVStrategy strategy) {
			this.strategy = strategy;
			return this;
		}

		/**
		 * Sets the entry parser that the CSVParser will use.
		 *
		 * @param entryParser the entry parser
		 * @return this builder
		 */
		public Builder<E> entryParser(CSVEntryParser<E> entryParser) {
			this.entryParser = entryParser;
			return this;
		}

		/**
		 * This method finally creates the CSVParser using the specified configuration.
		 *
		 * @return the CSVParser instance
		 */
		public CSVParser<E> build() {
			if (entryParser == null) {
				throw new IllegalStateException("you have to specify a csv entry parser");
			}

			return new CSVParser<E>(this);
		}
	}
}
