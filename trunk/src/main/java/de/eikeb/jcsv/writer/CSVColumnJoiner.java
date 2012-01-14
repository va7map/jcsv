package de.eikeb.jcsv.writer;

import de.eikeb.jcsv.CSVStrategy;

public interface CSVColumnJoiner {
	public String joinColumns(String[] data, CSVStrategy strategy);
}
