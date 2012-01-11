package de.eikeb.jcsv.writer;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import de.eikeb.jcsv.CSVStrategy;
import de.eikeb.jcsv.CSVUtil;

public class CSVWriter<E> {

	private final Writer writer;
	private final CSVStrategy strategy;
	private final CSVEntryConverter<E> entryConverter;

	private CSVWriter(Builder<E> builder) {
		this.writer = builder.writer;
		this.strategy = builder.strategy;
		this.entryConverter = builder.entryConverter;
	}


	/**
	 * Writes the data into the specified character output stream.
	 *
	 * @param data List of Es
	 * @throws IOException
	 */
	public void writeAll(List<E> data) throws IOException {
		for (E e : data) {
			StringBuilder sb = new StringBuilder();

			String[] columns = entryConverter.convertEntry(e);
			String line = CSVUtil.implode(columns, String.valueOf(strategy.getDelimiter()));

			sb.append(line);
			sb.append(System.getProperty("line.separator"));

			writer.append(sb.toString());
		}
	}

	public static class Builder<E> {
		private final Writer writer;
		private CSVStrategy strategy = CSVStrategy.DEFAULT;
		private CSVEntryConverter<E> entryConverter;

		/**
		 *
		 * @param writer the character output stream
		 */
		public Builder(Writer writer) {
			this.writer = writer;
		}

		/**
		 * Sets the strategy that the CSVWriter will use.
		 *
		 * @param strategy the csv strategy
		 * @return this builder
		 */
		public Builder<E> strategy(CSVStrategy strategy) {
			this.strategy = strategy;
			return this;
		}

		/**
		 * Sets the entry converter that the CSVWriter will use.
		 *
		 * @param entryConverter the entry converter
		 * @return this builder
		 */
		public Builder<E> entryConverter(CSVEntryConverter<E> entryConverter) {
			this.entryConverter = entryConverter;
			return this;
		}

		public CSVWriter<E> build() {
			if (entryConverter == null) {
				throw new IllegalStateException("you have to specify an entry converter");
			}

			return new CSVWriter<E>(this);
		}

	}
}
