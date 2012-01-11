package de.eikeb.jcsv.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.eikeb.jcsv.CSVStrategy;
import de.eikeb.jcsv.defaults.DefaultCSVEntryParser;

public class CSVReader<E> implements Iterable<E> {
	private final BufferedReader reader;
	private final CSVStrategy strategy;
	private final CSVEntryParser<E> entryParser;

	private boolean headerSkipped = false;

	private CSVReader(Builder<E> builder) {
		this.reader = new BufferedReader(builder.reader);
		this.strategy = builder.strategy;
		this.entryParser = builder.entryParser;
	}

	/**
	 * Returns a default configured CSVReader<String[]>.
	 * It uses the DefaultCSVEntryParser that allows you to
	 * convert a csv file into a List<String[]>.
	 *
	 * @param input the csv reader
	 * @return the CSVReader
	 */
	public static CSVReader<String[]> newDefaultReader(Reader input) {
		return new Builder<String[]>(input).entryParser(new DefaultCSVEntryParser()).build();
	}

	/**
	 * Reads the complete csv file and returns a List of created objects.
	 * Call readNext() multiple times until null is returned.
	 *
	 * @return List of E
	 * @throws IOException
	 */
	public List<E> readAll() throws IOException {
		List<E> entries = new ArrayList<E>();

		E entry = null;
		while ((entry = readNext()) != null) {
			entries.add(entry);
		}

		return entries;
	}

	/**
	 * Reads the next entry from the csv file and returns it.
	 *
	 * @return the next entry E, null if the end of the file has been reached
	 * @throws IOException
	 */
	public E readNext() throws IOException {
		if (strategy.isSkipHeader() && !headerSkipped) {
			reader.readLine();
			headerSkipped = true;
		}

		String line = null;
		do {
			line = reader.readLine();

			if (line == null) {
				return null;
			}
		} while (isCommentLine(line));



		String[] data = line.split(strategy.getDelimiterPattern());
		E entry = entryParser.parseEntry(data);

		return entry;
	}

	@Override
	public Iterator<E> iterator() {
		return new CSVIterator();
	}

	private boolean isCommentLine(String line) {
		return line.startsWith(String.valueOf(strategy.getCommentStart()));
	}

	private class CSVIterator implements Iterator<E> {
		private E nextEntry;

		@Override
		public boolean hasNext() {
			if (nextEntry != null) {
				return true;
			}

			try {
				nextEntry = readNext();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

			return nextEntry != null;
		}

		@Override
		public E next() {
			E entry = null;
			if (nextEntry != null) {
				entry = nextEntry;
				nextEntry = null;
			} else {
				try {
					entry = readNext();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

			return entry;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("this iterator doesn't support object deletion");
		}
	}

	/**
	 * The Builder that creates the CSVReader objects.
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
		 * Sets the strategy that the CSVReader will use.
		 *
		 * @param strategy the csv strategy
		 * @return this builder
		 */
		public Builder<E> strategy(CSVStrategy strategy) {
			this.strategy = strategy;
			return this;
		}

		/**
		 * Sets the entry parser that the CSVReader will use.
		 *
		 * @param entryParser the entry parser
		 * @return this builder
		 */
		public Builder<E> entryParser(CSVEntryParser<E> entryParser) {
			this.entryParser = entryParser;
			return this;
		}

		/**
		 * This method finally creates the CSVReader using the specified configuration.
		 *
		 * @return the CSVReader instance
		 */
		public CSVReader<E> build() {
			if (entryParser == null) {
				throw new IllegalStateException("you have to specify a csv entry parser");
			}

			return new CSVReader<E>(this);
		}
	}
}
