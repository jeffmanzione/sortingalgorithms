package com.jeffreymanzione.sorting;

import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

/**
 * ParallelSort.java
 * 
 * An interface describing the functionality of a parallel sort.
 * 
 * It is designed to allow custom comparators and parallel sorting.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since October 23, 2014
 *
 * @param <T>
 *            The type of elements which will be passed into {@link Sort#sort(Comparable[])}.
 */
public interface ParallelSort<T extends Comparable<T>> extends Sort<T> {

	/**
	 * The default parallel threshold: 2,048.
	 */
	public static final int DEFAULT_PARALLEL_THRESHOLD = 2_048;

	/**
	 * Blocks until the sorting is completed.
	 */
	public void awaitCompletion();

	/**
	 * Sorts the entire specified array in parallel. Note that this method does not block. It triggers the sorting, and
	 * then, it returns.
	 * 
	 * @param arr
	 *            The array to sort.
	 * 
	 * @see #sortParallel(Comparable[], int, int)
	 */
	public void sortParallel(T[] arr);

	/**
	 * Sorts the specified range of the specified array in parallel. Note that this method does not block. It triggers
	 * the sorting, and then, it returns.
	 * 
	 * @param arr
	 *            The array to be sorted
	 * @param start
	 *            The start of the range to be sorted in the array
	 * @param end
	 *            The end of the range to be sorted in the array
	 */
	public void sortParallel(T[] arr, int start, int end);

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

}
