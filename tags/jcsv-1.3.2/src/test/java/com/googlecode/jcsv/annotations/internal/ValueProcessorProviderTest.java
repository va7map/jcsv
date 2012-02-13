package com.googlecode.jcsv.annotations.internal;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.googlecode.jcsv.annotations.ValueProcessor;
import com.googlecode.jcsv.annotations.processors.IntegerProcessor;
import com.googlecode.jcsv.annotations.processors.StringProcessor;

public class ValueProcessorProviderTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// remove some value processors to test register method
		ValueProcessorProvider.removeValueProcessor(String.class);
		ValueProcessorProvider.removeValueProcessor(Integer.class);
		ValueProcessorProvider.removeValueProcessor(Float.class);
	}

	@Test
	public void testRegisterAndGetValueProcessor() {
		// add a simple Processor
		ValueProcessor<Integer> integerProcessor = new IntegerProcessor();
		ValueProcessorProvider.registerValueProcessor(Integer.class, integerProcessor);
		
		assertEquals(integerProcessor, ValueProcessorProvider.getValueProcessor(Integer.class));
		assertEquals(integerProcessor, ValueProcessorProvider.getValueProcessor(int.class));
		
		// add a processor for a sub class of type E
		ValueProcessor<String> stringProcessor = new StringProcessor();
		ValueProcessorProvider.registerValueProcessor(CharSequence.class, stringProcessor);
		
		assertEquals(stringProcessor, ValueProcessorProvider.getValueProcessor(CharSequence.class));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailRegisterValueProcessor() {
		ValueProcessor<Integer> integerProcessor = new IntegerProcessor();
		ValueProcessorProvider.registerValueProcessor(Integer.class, integerProcessor);
		ValueProcessorProvider.registerValueProcessor(Integer.class, integerProcessor);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRemoveValueProcessor() {
		ValueProcessor<Integer> integerProcessor = new IntegerProcessor();
		ValueProcessorProvider.registerValueProcessor(Integer.class, integerProcessor);
		
		assertEquals(integerProcessor, ValueProcessorProvider.getValueProcessor(Integer.class));
		
		ValueProcessorProvider.removeValueProcessor(Integer.class);
		ValueProcessorProvider.getValueProcessor(Integer.class);
	}
}
