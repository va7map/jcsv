package de.eikeb.jcsv.reader;

public interface CSVEntryFilter<E> {

	/**
	 * Checks whether the object e matches this filter.
	 *
	 * @param e The object that is to be tested
	 * @return true, if e matches this filter
	 */
	public boolean match(E e);
}
