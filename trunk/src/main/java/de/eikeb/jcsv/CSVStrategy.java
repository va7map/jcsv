package de.eikeb.jcsv;

public class CSVStrategy {

	/**
	 * The default CSV strategy.
	 * - delimiter is a ;
	 * - comments start with an #
	 * - will not skip the header line
	 */
	public static final CSVStrategy DEFAULT = new CSVStrategy(';', '#', false);

	/**
	 * The USA/UK csv standard.
	 * - delimiter is a ,
	 * - comments start with an #
	 * - will not skip the header line
	 */
	public static final CSVStrategy UK_DEFAULT = new CSVStrategy(',',  '#', false);

	private final char delimiter;
	private final char commentStart;
	private final boolean skipHeader;

	/**
	 * Creates a csv strategy, using the given parameters
	 * @param delimiter the token delimiter char
	 * @param commentStart the comment indicator
	 * @param skipHeader if true, skips the first line
	 */
	public CSVStrategy(char delimiter, char commentStart, boolean skipHeader) {
		this.delimiter = delimiter;
		this.commentStart = commentStart;
		this.skipHeader = skipHeader;
	}

	public char getDelimiter() {
		return delimiter;
	}

	public char getCommentStart() {
		return commentStart;
	}

	public boolean isSkipHeader() {
		return skipHeader;
	}
}
