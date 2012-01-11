package de.eikeb.jcsv.writer;
/**
 * The CSVEntryConverter receives a java object and converts it
 * into a String[] array that will be written to the output stream.
 *
 * @param <E> The Type that will be converted
 */
public interface CSVEntryConverter<E> {
	public String[] convertEntry(E e);
}
