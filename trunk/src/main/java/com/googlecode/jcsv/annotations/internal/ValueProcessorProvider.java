package com.googlecode.jcsv.annotations.internal;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.jcsv.annotations.ValueProcessor;
import com.googlecode.jcsv.annotations.processors.BooleanProcessor;
import com.googlecode.jcsv.annotations.processors.ByteProcessor;
import com.googlecode.jcsv.annotations.processors.CharacterProcessor;
import com.googlecode.jcsv.annotations.processors.DateProcessor;
import com.googlecode.jcsv.annotations.processors.DoubleProcessor;
import com.googlecode.jcsv.annotations.processors.FloatProcessor;
import com.googlecode.jcsv.annotations.processors.IntegerProcessor;
import com.googlecode.jcsv.annotations.processors.LongProcessor;
import com.googlecode.jcsv.annotations.processors.ShortProcessor;
import com.googlecode.jcsv.annotations.processors.StringProcessor;

/**
 * The ValueProcessorProvider is a static cache for the ColumnProcessors.
 * It is a cache that maps Classes to ColumnProcessors. The AnnotationEntryParser
 * uses this cache to retrieve the the processor for a specific class.
 *
 * There a several preconfigured processors, such as the primitives, its wrapper
 * classes and the String class.
 * If you want to add a new processor, you can register one using the
 * registerColumnProcessor method.
 *
 */
public class ValueProcessorProvider {

	private final Map<Class<?>, ValueProcessor<?>> processors = new HashMap<Class<?>, ValueProcessor<?>>();

	private final Map<Class<?>, Class<?>> primitiveWrapperTypes = new HashMap<Class<?>, Class<?>>();

	public ValueProcessorProvider() {
		fillPrimitiveWrapperTypesMap();
		registerDefaultValueProcessors();
	}

	/**
	 * Registers a ValueProcessor for class clazz.
	 *
	 * @param clazz the class that the processor should convert
	 * @param processor the processor
	 */
	public <E> void registerValueProcessor(Class<E> clazz, ValueProcessor<? extends E> processor) {
		if (clazz.isPrimitive()) {
			throw new IllegalArgumentException(
					"can not register value processor for a primitive type, register it for the wrapper type instead");
		}

		if (processors.containsKey(clazz)) {
			throw new IllegalArgumentException(String.format(
					"can not register value processor for %s, it is already registered.", clazz));
		}

		processors.put(clazz, processor);
	}

	/**
	 * Removes a ValueProcessor from the cache. You have to call this method
	 * before registering a new value processor for an existing class.
	 *
	 * @param clazz the class
	 */
	public <E> void removeValueProcessor(Class<E> clazz) {
		if (!processors.containsKey(clazz)) {
			throw new IllegalArgumentException(String.format(
					"can not remove value processor for %s, it is not registered yet.", clazz));
		}

		processors.remove(clazz);
	}

	/**
	 * Returns the value processor for class clazz.
	 *
	 * @param clazz the class
	 * @return the appropriate value processor
	 */
	@SuppressWarnings("unchecked")
	public <E> ValueProcessor<E> getValueProcessor(Class<E> clazz) {
		if (clazz.isPrimitive()) {
			// this cast is safe
			clazz = (Class<E>) primitiveWrapperTypes.get(clazz);
		}

		if (!processors.containsKey(clazz)) {
			throw new IllegalArgumentException(String.format("no value processor registered for %s.", clazz));
		}

		// this cast is safe due to the registerValueProcessor method
		return ((ValueProcessor<E>) processors.get(clazz));
	}
	
	private void registerDefaultValueProcessors() {
		registerValueProcessor(String.class, new StringProcessor());
		registerValueProcessor(Boolean.class, new BooleanProcessor());
		registerValueProcessor(Byte.class, new ByteProcessor());
		registerValueProcessor(Character.class, new CharacterProcessor());
		registerValueProcessor(Double.class, new DoubleProcessor());
		registerValueProcessor(Float.class, new FloatProcessor());
		registerValueProcessor(Integer.class, new IntegerProcessor());
		registerValueProcessor(Long.class, new LongProcessor());
		registerValueProcessor(Short.class, new ShortProcessor());
		registerValueProcessor(Date.class, new DateProcessor(DateFormat.getDateInstance()));
	}
	
	private void fillPrimitiveWrapperTypesMap() {
		primitiveWrapperTypes.put(boolean.class, Boolean.class);
		primitiveWrapperTypes.put(byte.class, Byte.class);
		primitiveWrapperTypes.put(char.class, Character.class);
		primitiveWrapperTypes.put(double.class, Double.class);
		primitiveWrapperTypes.put(float.class, Float.class);
		primitiveWrapperTypes.put(int.class, Integer.class);
		primitiveWrapperTypes.put(long.class, Long.class);
		primitiveWrapperTypes.put(short.class, Short.class);		
	}	
}