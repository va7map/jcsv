package com.googlecode.jcsv.reader;

import java.io.Closeable;
import java.util.ListIterator;

/**
 * The CacheCSVReader improves the CSVReader with a cache for
 * the read entries.
 * If you need to access the records very often or want to iterate
 * through the list of records back and forth, you might use a
 * cached csv reader.
 *
 * This Interface bundles the methods of the ListIterator and Closeable.
 *
 * @param <E> the type of the records
 */
public interface CachedCSVReader<E> extends ListIterator<E>, Closeable {
}
