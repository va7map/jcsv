package de.eikeb.jcsv.writer.internal;

import de.eikeb.jcsv.CSVStrategy;
import de.eikeb.jcsv.util.CSVUtil;
import de.eikeb.jcsv.writer.CSVColumnJoiner;

/**
 * This is a simple implementation of the CSVColumnJoiner.
 * It just performs an <code>CSVUtil.implode</code> on the
 * incoming String array.
 *
 * If you need a full support of the CSV formatting standard
 * you should use
 * {@link de.eikeb.jcsv.writer.internal.CSVColumnJoinerImpl}
 */
public class SimpleCSVColumnJoiner implements CSVColumnJoiner {

	/**
	 * Performs a CSVUtil.implode() to concat the columns, it uses
	 * the delimiter specified by the csv strategy.
	 */
	@Override
	public String joinColumns(String[] data, CSVStrategy strategy) {
		return CSVUtil.implode(data, String.valueOf(strategy.getDelimiter()));
	}

}
