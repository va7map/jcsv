package com.googlecode.jcsv.annotations.processors;

import com.googlecode.jcsv.annotations.ValueProcessor;

/**
 * Processes boolean values.
 *
 * @author Eike Bergmann
 *
 */
public class BooleanProcessor implements ValueProcessor<Boolean> {
	/**
	 * Converts value into a boolean using {@link Boolean#parseBoolean(String)}
	 *
	 * @return Boolean the result
	 */
	@Override
	public Boolean processValue(String value) {
		return Boolean.parseBoolean(value);
	}
}
