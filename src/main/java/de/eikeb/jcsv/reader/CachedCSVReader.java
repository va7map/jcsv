package de.eikeb.jcsv.reader;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CachedCSVReader<E> implements ListIterator<E>, Closeable {

	private final CSVReader<E> reader;

	private final List<E> cachedEntries;
	private int currentIndex;

	public CachedCSVReader(CSVReader<E> reader) {
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

	private void cacheNextEntry() {
		try {
			E entry = reader.readNext();
			if (entry != null) {
				cachedEntries.add(entry);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
