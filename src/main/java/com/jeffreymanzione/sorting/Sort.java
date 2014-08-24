package com.jeffreymanzione.sorting;

import java.util.Comparator;

import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

/**
 * Sort.java
 * 
 * An interface describing the functionality of a sort.
 * 
 * It is designed to allow custom comparators and parallel sorting.
 * 
 * @author Jeff
 * @version 0.1
 * @since August 22, 2014
 *
 * @param <T>
 *            The type of elements which will be passed into {@link Sort#sort(Comparable[])}.
 */
public interface Sort<T extends Comparable<T>> {
	/**
	 * The default parallel threshold: 2,048.
	 */
	public static final int DEFAULT_PARALLEL_THRESHOLD = 2_048;

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
	 * Returns <b>true</b> if sort is capable of being run in parallel mode, <b>false</b> otherwise.
	 * 
	 * @return <b>true</b> if sort is capable of being run in parallel mode, <b>false</b> otherwise.
	 */
	public boolean isParallel();

	/**
	 * Sets the sort to be parallel. Note that this cannot make a sequential sort parallel, it only can force a parallel
	 * sort to work on one thread if the parameter isParallel is false. {@link #isParallel()} serves the purpose to
	 * identify a sort as being parallel or sequential.
	 * 
	 * @param isParallel
	 *            Should the sort be executed using a thread pool.
	 * @throws SortIsNotParallelException
	 *             If isParallel is true and {@link #isParallel()} returns false: meaning that the sort is not capable
	 *             of being parallel.
	 */
	public void setParallel(boolean isParallel) throws SortIsNotParallelException;

	/**
	 * Sets the parallel threshold for the sort.
	 * 
	 * @param minNumElements
	 *            The threshold for converting to serial from parallel sorting.
	 * @throws SortIsNotParallelException
	 *             If {@link #isParallel()} returns false: meaning that the sort is not capable of being parallel.
	 */
	public void setParallelThreshold(int minNumElements) throws SortIsNotParallelException;

	/**
	 * Gets the parallel threshold for the sort.
	 * 
	 * @return The parallel threshold. By default {@link #DEFAULT_PARALLEL_THRESHOLD}.
	 */
	public int getParallelThreshold();

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
}
