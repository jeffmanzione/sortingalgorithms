/**
 * 
 */
package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.AbstractSort;

/**
 * Introsort.java
 * 
 * An implementation of Introsort. Introsort is a combination of Quicksort and Heapsort. Quicksort is performed until
 * the specified recursion depth is reached (default is the floor of 2log(2n)). Once the number of elements in a
 * partition is 16 or less, insertion sort is performed. If there are only 2 or 3 elements, they are manually compared
 * and swapped.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 31, 2014
 *
 */
public class Introsort<T extends Comparable<T>> extends AbstractSort<T> {

	private InsertionSort<T> insertionSort;
	private Heapsort<T> heapsort;
	private Quicksort<T> quicksort;

	public enum RecusionDepthScheme {
		Depth_2nLog2n, Depth_2nlogn, Depth_nlogn;
	}

	private RecusionDepthScheme recursionDepthScheme;

	private int recursionDepthThreshold = -1;

	/**
	 * Default constructor.
	 */
	public Introsort() {
		super();
		this.recursionDepthScheme = RecusionDepthScheme.Depth_2nLog2n;
	}

	/**
	 * Constructor which sets the recursion depth scheme.
	 * 
	 * @param recursionDepthScheme
	 */
	public Introsort(RecusionDepthScheme recursionDepthScheme) {
		super();
		this.recursionDepthScheme = recursionDepthScheme;
	}

	/**
	 * Sets the recursion depth scheme.
	 * 
	 * @param recursionDepthScheme
	 *            The recursion depth scheme
	 */
	public void setRecursionDepthScheme(RecusionDepthScheme recursionDepthScheme) {
		this.recursionDepthScheme = recursionDepthScheme;
	}

	/**
	 * Gets the recursion depth scheme of this instance of Introsort.
	 * 
	 * @return the recursion depth scheme of this instance of Introsort
	 */
	public RecusionDepthScheme getRecursionDepthScheme() {
		return this.recursionDepthScheme;
	}

	/**
	 * Sets the recursion depth scheme to the default.
	 * 
	 * @see RecusionDepthScheme#Depth_2nLog2n
	 */
	public void resetRecursionDepthSceme() {
		this.recursionDepthScheme = RecusionDepthScheme.Depth_2nLog2n;
	}

	@Override
	public boolean isParallel() {
		return true;
	}

	@Override
	protected void sortImplementation(T[] arr, int start, int end, int recursionDepth, int originalLength) {
		if (end - start > 1) {
			if (this.recursionDepthThreshold == -1) {
				insertionSort = new InsertionSort<>();
				insertionSort.setComparator(this.getComparator());

				heapsort = new Heapsort<>();
				heapsort.setComparator(this.getComparator());

				quicksort = new Quicksort<>();
				quicksort.setComparator(this.getComparator());

				switch (recursionDepthScheme) {
					case Depth_2nlogn:
						this.recursionDepthThreshold = (int) Math.floor(2 * (end - start) * Math.log(end - start));
						break;
					case Depth_nlogn:
						this.recursionDepthThreshold = (int) Math.floor((end - start) * Math.log(end - start));
						break;
					default:
						this.recursionDepthThreshold = (int) Math
								.floor(2 * (end - start) * Math.log(2 * (end - start)));
				}

			}

			/*
			 * Insertion sort the range if there are 16 elements or fewer.
			 */
			if (end - start < 16) {
				insertionSort.sortImplementation(arr, start, end, recursionDepth + 1, originalLength);
			}
			/*
			 * Heapsort if the recursion depth has reached the specified threshold.
			 */
			else if (recursionDepth >= this.recursionDepthThreshold) {
				heapsort.sortImplementation(arr, start, end, recursionDepth + 1, originalLength);
			}
			/*
			 * Otherwise use the partition method from quicksort and quicksort the elements.
			 */
			else {
				Range[] partitions = quicksort.partition(arr, start, end - 1, recursionDepth, originalLength);
				if (partitions != null) {
					Range range1 = partitions[0];

					if (range1.end - range1.start > getParallelThreshold()) {
						this.subsort(arr, range1.start, range1.end, recursionDepth + 1, originalLength);
					} else {
						this.sortImplementation(arr, range1.start, range1.end, recursionDepth + 1, originalLength);
					}

					if (partitions.length > 1) {
						Range range2 = partitions[1];

						if (range2.end - range2.start > getParallelThreshold()) {
							this.subsort(arr, range2.start, range2.end, recursionDepth + 1, originalLength);
						} else {
							this.sortImplementation(arr, range2.start, range2.end, recursionDepth + 1, originalLength);
						}
					}

				}
			}
		}
	}

}
