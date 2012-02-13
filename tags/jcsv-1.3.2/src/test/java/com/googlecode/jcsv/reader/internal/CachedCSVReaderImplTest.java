package com.googlecode.jcsv.reader.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.util.Person;
import com.googlecode.jcsv.util.PersonEntryParser;

public class CachedCSVReaderImplTest {

	private CachedCSVReaderImpl<Person> cachedReader;

	@Before
	public void setUp() throws Exception {
		Reader reader = new InputStreamReader(this.getClass().getResourceAsStream("/persons.csv"));
		CSVStrategy strategy = new CSVStrategy(';', '"', '#', true, true);
		CSVReader<Person> csvReader = new CSVReaderBuilder<Person>(reader).entryParser(new PersonEntryParser()).strategy(strategy).build();
		cachedReader = new CachedCSVReaderImpl<Person>(csvReader);
	}

	@After
	public void tearDown() throws Exception {
		cachedReader.close();
	}

	@Test
	public void testHasNext() {
		// The cachedReader has 2 entries
		assertTrue(cachedReader.hasNext());
		cachedReader.next();
		assertTrue(cachedReader.hasNext());
		cachedReader.next();
		assertFalse(cachedReader.hasNext());
	}

	@Test
	public void testNext() {
		Person result = cachedReader.next();
		Person expected = new Person("Hans", "im Glück", 16);
		assertEquals(expected, result);

		result = cachedReader.next();
		expected = new Person("Klaus", "Meyer", 33);
		assertEquals(expected, result);
	}

	@Test(expected=NoSuchElementException.class)
	public void testListBounds() {
		cachedReader.next();
		cachedReader.next();
		cachedReader.next();
	}

	@Test
	public void testHasPrevious() {
		assertFalse(cachedReader.hasPrevious());

		cachedReader.next();
		assertFalse(cachedReader.hasPrevious());

		cachedReader.next();
		assertTrue(cachedReader.hasPrevious());

		cachedReader.previous();
		assertFalse(cachedReader.hasPrevious());
	}

	@Test
	public void testPrevious() {
		Person expected = cachedReader.next();
		cachedReader.next();

		assertSame(expected, cachedReader.previous());

	}

	@Test
	public void testNextIndex() {
		// first call has to return 0
		assertSame(0, cachedReader.nextIndex());

		// move to the first entry
		cachedReader.next();
		assertSame(1, cachedReader.nextIndex());

		// move to the end of the list, there will be no next entry
		cachedReader.next();
		assertSame(2, cachedReader.nextIndex());
	}

	@Test
	public void testPreviousIndex() {
		cachedReader.next();

		// first call has to return -1, hence we are at the beginning of the list
		assertSame(-1, cachedReader.previousIndex());

		// move to the next entry
		cachedReader.next();
		assertSame(0, cachedReader.previousIndex());
	}

	@Test
	public void testGet() {
		Person result = cachedReader.get(1);
		Person expected = new Person("Klaus", "Meyer", 33);
		assertEquals(expected, result);

		result = cachedReader.get(0);
		expected = new Person("Hans", "im Glück", 16);
		assertEquals(expected, result);

		result = cachedReader.get(1);
		expected = new Person("Klaus", "Meyer", 33);
		assertEquals(expected, result);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testGetFails() {
		cachedReader.get(42);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {
		cachedReader.remove();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testSet() {
		cachedReader.set(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAdd() {
		cachedReader.add(null);
	}
}
