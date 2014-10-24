package com.jeffreymanzione.sorting;

import java.util.Comparator;

/**
 * AbstractSort.java
 * 
 * An abstract implementation of <b>Sort</b>. Implements the core functionality and helper methods of a Sort.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 24, 2014
 * @see Sort
 * @param <T>
 *            The parameterized type
 */
public abstract class AbstractSort<T extends Comparable<T>> implements Sort<T> {

	/*
	 * Comparators fields
	 */
	private Comparator<T> comparator;
	private boolean hasComparator;

	protected AbstractSort() {
		hasComparator = false;
	}

	protected AbstractSort(Comparator<T> comparator) {
		this();
		this.setComparator(comparator);
	}

	/**
	 * Compares the specified elements. Works the same as {@link Comparator#compare(Object, Object)}. The purpose is to
	 * abstract the comparator from the developer.
	 * 
	 * @param t1
	 *            The first element to be sorted
	 * @param t2
	 *            The second element t
	 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
	 *         than the second.
	 */
	protected int compare(T t1, T t2) {
		if (hasComparator) {
			return comparator.compare(t1, t2);
		} else {
			return t1.compareTo(t2);
		}
	}

	@Override
	public void setComparator(Comparator<T> comparator) {
		if (comparator != null) {
			this.comparator = comparator;
			hasComparator = true;
		}
	}

	@Override
	public Comparator<T> getComparator() {
		return comparator;
	}

	@Override
	public boolean removeComparator() {
		if (hasComparator) {
			this.comparator = null;
			hasComparator = false;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void sort(T[] arr) {
		sort(arr, 0, arr.length);

	}

	@Override
	public void sort(T[] arr, int start, int end) {
		sortImplementation(arr, start, end, 0, end - start);
	}

	/**
	 * The implementation of the sort which is indirectly called by {@link #sort(Comparable[])} and
	 * {@link #sort(Comparable[], int, int)}. The proper implementation of this function should uphold the contract that
	 * it sorts the list using the {@link #compare(Comparable, Comparable)} method. There is no built-in check as to
	 * whether the sort successfully sorted the elements in the specified range. The implementation is not required to
	 * block the return of this method in the case of pending subsorts as {@link #sort(Comparable[])} and
	 * {@link #sort(Comparable[], int, int)} will wait for the completion of all pending subsorts before returning.
	 * 
	 * @param arr
	 *            The array to be sorted
	 * @param start
	 *            The starting index of the portion of the array to be sorted inclusive
	 * @param end
	 *            The ending index of the portion of the array to be sorted exclusive
	 * @param recursionDepth
	 *            the number of recursion on this sort before this iteration of sorting is called. (This is for sorts
	 *            where recursion depth matters such as with Introsort)
	 * @param originalLength
	 *            The original length of the array to be sorted.
	 * 
	 */
	protected abstract void sortImplementation(T[] arr, int start, int end, int recursionDepth, int originalLength);

}
