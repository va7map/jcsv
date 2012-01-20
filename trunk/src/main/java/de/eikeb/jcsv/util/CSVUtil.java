package de.eikeb.jcsv.util;

/**
 * Provides some useful functions.
 */
public class CSVUtil {
	private CSVUtil() {
		// Prevent instantiating and inheriting
	}

	/**
	 * Concats the String[] array data to a single String, using the specified delimiter
	 * as the glue.
	 *
	 * <code>implode({"A", "B", "C"}, ";")</code>
	 * would result in A;B;C
	 *
	 * @param data the strings that should be concatinated
	 * @param delimiter the delimiter
	 * @return the concatinated string
	 */
	public static String implode(String[] data, String delimiter) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (i != 0) {
				sb.append(delimiter);
			}

			sb.append(data[i]);
		}

		return sb.toString();
	}
}
