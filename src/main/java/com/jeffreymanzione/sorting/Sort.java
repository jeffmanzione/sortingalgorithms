package com.jeffreymanzione.sorting;

import java.util.Comparator;

/**
 * Sort.java
 * 
 * An interface describing the functionality of a sort.
 * 
 * It is designed to allow custom comparators.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 22, 2014
 *
 * @param <T>
 *            The type of elements which will be passed into {@link Sort#sort(Comparable[])}.
 */
public interface Sort<T extends Comparable<T>> {

	/**
	 * Sets the comparator for the sort. The default is to use {@link Comparable#compareTo(Object)}, but if a comparator
	 * is specified, it will be used instead.
	 * 
	 * @param comparator
	 *            The comparator
	 */
	public void setComparator(Comparator<T> comparator);

	/**
	 * Gets the comparator passed with the {@link #setComparator(Comparator)} method. If no comparator is present, null
	 * is returned.
	 * 
	 * @return The comparator used by the sort unless none has been specified.
	 */
	public Comparator<T> getComparator();

	/**
	 * Removes the comparator for the sort.
	 * 
	 * @return <b>true</b> if the comparator was cleared, <b>false</b> if no comparator was specified.
	 */
	public boolean removeComparator();

	/**
	 * Sorts the entire specified array.
	 * 
	 * @param arr
	 *            The array to sort.
	 */
	public void sort(T[] arr);

	/**
	 * Sorts the specified range of the specified array.
	 * 
	 * @param arr
	 *            The array to be sorted
	 * @param start
	 *            The start of the range to be sorted in the array
	 * @param end
	 *            The end of the range to be sorted in the array
	 */
	public void sort(T[] arr, int start, int end);
	
	/**
	 * Range representing values from start (inclusive) to end (exclusive).
	 * 
	 * @author Jeffrey J. Manzione
	 * @version 0.1
	 * @since August 31, 2014
	 *
	 */
	public class Range {
		public int start, end;

		/**
		 * Range constructor.
		 * 
		 * @param start
		 *            The start of the range (inclusive).
		 * @param end
		 *            The end of the range (exclusive).
		 */
		public Range(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}
}
