package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.AbstractSort;

/**
 * Quicksort implementation. As it is written, it is a parallel sort. This sort is by partition. The middle element of
 * the array is selected as a pivot. all elements are compared to the pivot. If {@link #compare(Comparable, Comparable)}
 * {@literal >} 0 the pivot, the element is brought to the left of the pivot. If
 * {@link #compare(Comparable, Comparable)} {@literal <} 0, it goes to the right of the pivot. Once the partition is
 * complete, the two partitioned portions are quicksorted until there only remains one element to be quicksorted.
 * 
 * @author Jeff
 * @version 0.1
 * @since August 23, 2014
 *
 * @param <T>
 *            Parameterized type implements Comparable of that type
 */
public class Quicksort<T extends Comparable<T>> extends AbstractSort<T> {

	@Override
	public boolean isParallel() {
		return true;
	}

	protected void sortImplementation(T[] arr, int start, int end) {
		end--;
		// System.out.println("RANGE=[" + start + "," + end + ")");
		if (end - start > 0) {
			T pivot = arr[(int) (start + (end - start) / 2)];
			int left = start;
			int right = end;

			while (left <= right) {
				// System.out.println("LEFT=" + left + " RIGHT=" + right);
				while (compare(arr[left], pivot) < 0) {
					left++;
				}
				while (compare(arr[right], pivot) > 0) {
					right--;
				}
				if (left <= right) {
					T tmp = arr[left];
					arr[left] = arr[right];
					arr[right] = tmp;
					left++;
					right--;
				}
			}

			// System.out.println(left + " " + right + " " + start + " " + end);
			boolean performedLeft = true, performedRight = true;

			if (end - left > getParallelThreshold()) {
				this.subsort(arr, left, end + 1);
			} else if (end - left == 1) {
				if (compare(arr[left], arr[end]) > 0) {
					T tmp = arr[left];
					arr[left] = arr[end];
					arr[end] = tmp;
				}
			} else if (end - left == 2) {
				if (compare(arr[left], arr[left + 1]) > 0) {
					T tmp = arr[left];
					arr[left] = arr[left + 1];
					arr[left + 1] = tmp;
				}

				if (compare(arr[left + 1], arr[end]) > 0) {
					T tmp = arr[left + 1];
					arr[left + 1] = arr[end];
					arr[end] = tmp;

					if (compare(arr[left], arr[left + 1]) > 0) {
						tmp = arr[left];
						arr[left] = arr[left + 1];
						arr[left + 1] = tmp;
					}

				}

			} else {
				performedLeft = false;
			}

			if (right - start > getParallelThreshold()) {
				this.subsort(arr, start, right + 1);
			} else if (right - start == 1) {
				if (compare(arr[right], arr[start]) < 0) {
					T tmp = arr[right];
					arr[right] = arr[start];
					arr[start] = tmp;
				}
			} else if (right - start == 2) {
				if (compare(arr[start], arr[start + 1]) > 0) {
					T tmp = arr[start];
					arr[start] = arr[start + 1];
					arr[start + 1] = tmp;
				}

				if (compare(arr[start + 1], arr[right]) > 0) {
					T tmp = arr[start + 1];
					arr[start + 1] = arr[right];
					arr[right] = tmp;

					if (compare(arr[start], arr[start + 1]) > 0) {
						tmp = arr[start];
						arr[start] = arr[start + 1];
						arr[start + 1] = tmp;
					}
				}

			} else {
				performedRight = false;
			}

			if (!performedLeft) {
				sortImplementation(arr, left, end + 1);
			}
			if (!performedRight) {
				sortImplementation(arr, start, right + 1);
			}

		}

	}

}
