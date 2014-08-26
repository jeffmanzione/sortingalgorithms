package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.AbstractSort;

/**
 * InsertionSort.java
 * 
 * Binary Insertion sort implementation. Note that this sort generally performs in O(nlogn) time. The algorithm works by
 * maintaining a sorted portion on the left side and sequentially inserting elements from left to right into its sorted
 * position via a binary search of the sorted portion of the array. These insertions continue until the unsorted portion
 * of the array is empty.
 * 
 * Text diagram: [ sorted | x | unsorted ]
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 24, 2014
 *
 * @param <T>
 *            Parameterized type implements Comparable of that type
 */
public class InsertionSort<T extends Comparable<T>> extends AbstractSort<T> {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	protected void sortImplementation(T[] arr, int start, int end) {
		// long time = System.nanoTime();
		if (end - start > 1) {
			T tmp;
			for (int i = start + 1; i < end; i++) {
				tmp = arr[i];

				int rangeStart = start, rangeEnd = i;
				int mid;

				while (rangeStart < rangeEnd) {
					mid = (rangeStart + rangeEnd) / 2;
					if (compare(tmp, arr[mid]) >= 0) {
						rangeStart = mid + 1;
					} else {
						rangeEnd = mid;
					}
				}

				int k;
				for (k = i; k > rangeEnd; k--) {
					arr[k] = arr[k - 1];
				}

				// System.out.println(arr[k] + " le " + tmp);

				arr[k] = tmp;

			}
		}

		// System.out.println(System.nanoTime() - time);
	}

	@Deprecated
	protected void sortImplementationNotBinary(T[] arr, int start, int end) {
		// long time = System.nanoTime();
		if (end - start > 1) {
			T tmp;
			for (int i = start + 1; i < end; i++) {
				tmp = arr[i];
				int j;

				for (j = i - 1; j >= start && compare(arr[j], tmp) > 0; j--)
					;

				for (int k = i; k > j + 1; k--) {
					arr[k] = arr[k - 1];
				}
				arr[j + 1] = tmp;
			}
		}
		// System.out.println(System.nanoTime() - time);
	}

}
