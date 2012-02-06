package com.googlecode.jcsv.reader.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.CachedCSVReader;


public class CachedCSVReaderImpl<E> implements CachedCSVReader<E> {

	private final CSVReader<E> reader;

	private final List<E> cachedEntries;
	private int currentIndex;

	public CachedCSVReaderImpl(CSVReader<E> reader) {
		this.reader = reader;
		this.cachedEntries = new ArrayList<E>();

		currentIndex = -1;
	}

	@Override
	public boolean hasNext() {
		if (currentIndex + 1 >= cachedEntries.size()) {
			cacheNextEntry();
		}

		return currentIndex + 1 < cachedEntries.size();
	}

	@Override
	public E next() {
		if (!hasNext()) {
			throw new NoSuchElementException(String.format("size: %s, index: %s", cachedEntries.size(), currentIndex + 1));
		}

		currentIndex++;
		return cachedEntries.get(currentIndex);
	}

	@Override
	public boolean hasPrevious() {
		return currentIndex > 0;
	}

	@Override
	public E previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException(String.format("size: %s, index: %s", cachedEntries.size(), currentIndex - 1));
		}

		currentIndex--;
		return cachedEntries.get(currentIndex);
	}

	@Override
	public int nextIndex() {
		if (currentIndex >= cachedEntries.size()) {
			cacheNextEntry();
		}

		if (currentIndex >= cachedEntries.size()) {
			return cachedEntries.size();
		}

		return currentIndex + 1;
	}

	@Override
	public int previousIndex() {
		return currentIndex - 1;
	}

	@Override
	public E get(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("i has to be greater 0, but was " + index);
		}

		readUntil(index);

		if (cachedEntries.size() < index) {
			throw new ArrayIndexOutOfBoundsException(String.format("size: %s, index: %s", cachedEntries.size(), index));
		}

		return cachedEntries.get(index);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove not allowed");
	}

	@Override
	public void set(Object e) {
		throw new UnsupportedOperationException("set not allowed");
	}

	@Override
	public void add(Object e) {
		throw new UnsupportedOperationException("add not allowed");
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

	private void readUntil(int i) {
		while (cacheNextEntry() && cachedEntries.size() <= i);
	}

	private boolean cacheNextEntry() {
		boolean success = false;
		try {
			E entry = reader.readNext();
			if (entry != null) {
				cachedEntries.add(entry);
				success = true;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return success;
	}

}
