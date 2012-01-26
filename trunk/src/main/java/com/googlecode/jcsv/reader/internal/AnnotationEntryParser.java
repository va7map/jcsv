package com.googlecode.jcsv.reader.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.googlecode.jcsv.annotations.MapToColumn;
import com.googlecode.jcsv.annotations.ValueProcessor;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;

/**
 * Parses a csv entry, based on an annotated class.
 *
 * @author Eike Bergmann
 *
 * @param <E> the type of the csv entries
 */
public class AnnotationEntryParser<E> implements CSVEntryParser<E> {

	private final Class<E> clazz;

	/**
	 * Constructs a AnnotationEntryParser for type E.
	 *
	 * @param clazz the annotated class, and the class of the csv entries
	 */
	public AnnotationEntryParser(Class<E> clazz) {
		this.clazz = clazz;
	}

	/**
	 * {@link CSVEntryParser#parseEntry(String...)}
	 */
	@Override
	public E parseEntry(String... data) {
		E entry = newClassIntance();

		fillObject(entry, data);

		return entry;
	}

	private E newClassIntance() {
		E entry;
		try {
			entry = clazz.newInstance();
		} catch (InstantiationException ie) {
			throw new RuntimeException(String.format("can not instantiate class %s", clazz.getName()), ie);
		} catch (IllegalAccessException iae) {
			throw new RuntimeException(String.format("can not access class %s", clazz.getName()), iae);
		}

		return entry;
	}

	// TODO: refactor
	private void fillObject(E entry, String[] data) {
		for (Field field : entry.getClass().getDeclaredFields()) {
			for (Annotation annotation : field.getAnnotations()) {
				if (annotation instanceof MapToColumn) {
					MapToColumn mapAnnotation = (MapToColumn) annotation;

					int column = mapAnnotation.column();
					Class<?> type;
					if (mapAnnotation.type().equals(MapToColumn.Default.class)) {
						// use the fild type
						type = field.getType();
					} else {
						// use the annotated type
						type = mapAnnotation.type();
					}

					ValueProcessor<?> cp = ValueProcessorProvider.getValueProcessor(type);

					Object value = cp.processColumn(data[column]);
					boolean isAccessible = field.isAccessible();
					field.setAccessible(true);
					try {
						field.set(entry, value);
					} catch (IllegalArgumentException iae) {
						throw new RuntimeException(String.format("can not set value %s for type %s", value, type), iae);
					} catch (IllegalAccessException iae) {
						throw new RuntimeException(String.format("can not access field %s", field), iae);
					}
					field.setAccessible(isAccessible);
				}
			}
		}
	}
}