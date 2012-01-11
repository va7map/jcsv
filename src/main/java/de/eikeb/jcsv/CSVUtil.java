package de.eikeb.jcsv;

/**
 * Provides some useful functions.
 */
public class CSVUtil {
	private CSVUtil() {
		// Prevent instantiating and inheriting
	}

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
