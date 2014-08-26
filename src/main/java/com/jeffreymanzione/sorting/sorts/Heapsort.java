package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.AbstractSort;

/**
 * An implementation of Heapsort as described first by J. W. J Williams and optimized by Robert W. Floyd. The algorithm
 * works by creating a max heap out of the elements using the rule that the left child and right child of a node n are
 * 2n + 1 and 2n + 2 respectively. Once heapified, the largest element (at index 0) is swapped with the last unsorted
 * and is chopped off of the heap. The heap is reheapified. This is done untill the remaining heap is of size 0.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 25, 2014
 * 
 * @param <T>
 *            The parameterized type
 */
public class Heapsort<T extends Comparable<T>> extends AbstractSort<T> {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	protected void sortImplementation(T[] arr, int start, int end) {
		/* Establish the initial heap property */
		heapify(arr, start, end);
		/* Sequential remove elements from the heap */
		while ((end - start) > 1) {
			/* Decrease the size of the heap */
			end--;
			/* Swap the root of the max heap with the element just beyond the size of the heap. */
			T tmp = arr[start];
			arr[start] = arr[end];
			arr[end] = tmp;
			/* Reestablish the heap property */
			sift(arr, start, end);
		}

	}

	/**
	 * Applied to the array to establish the heap structure at the beginning of the sort.
	 * 
	 * @param arr
	 *            Array to heapify
	 * @param start
	 *            The lower bound of the heap inclusive
	 * @param end
	 *            The upper bound of the heap exclusive
	 */
	private void heapify(T[] arr, int start, int end) {
		int i = (int) Math.pow(2, (int) (Math.log10(end - start) / Math.log10(2)) - 1) - 1
				+ (end - start - (int) Math.pow(2, (int) (Math.log10(end - start) / Math.log10(2))) + 1 + 1) / 2;

		while (i >= 0) {
			boolean heaped = false;
			int k = i;
			while (!heaped) {
				int left = left(k, start, end), right = right(k, start, end);

				if (left != -1) {
					if (right != -1) {
						int cmp = compare(arr[left], arr[right]);
						if (cmp > 0) {
							if (compare(arr[left], arr[k]) > 0) {
								T tmp = arr[left];
								arr[left] = arr[k];
								arr[k] = tmp;
								k = left;
							} else {
								heaped = true;
							}
						} else if (cmp < 0) {
							if (compare(arr[right], arr[k]) > 0) {
								T tmp = arr[right];
								arr[right] = arr[k];
								arr[k] = tmp;
								k = right;
							} else {
								heaped = true;

							}
						} else {
							heaped = true;
						}
					} else {
						if (compare(arr[left], arr[k]) > 0) {
							T tmp = arr[left];
							arr[left] = arr[k];
							arr[k] = tmp;
							k = left;
						} else {
							heaped = true;
						}
					}
				} else {
					heaped = true;
				}
			}
			i--;
		}

	}

	/**
	 * Sifts the new head (previously the leaf farthest left) down the greedy maximum path and then sifts it back up to
	 * reestablish the heap property.
	 * 
	 * @param arr
	 *            Array to heapify
	 * @param start
	 *            The lower bound of the heap inclusive
	 * @param end
	 *            The upper bound of the heap exclusive
	 */
	private void sift(T[] arr, int start, int end) {
		/*
		 * Top becomes the element to bubble down.
		 */
		int i = start, left, right;
		T bubble = arr[start];
		/*
		 * Sift the root to a leaf via greedy maximum path to minimize the comparisons and swaps. See Floyd's sift-up
		 * and sift-down.
		 */
		while ((left = left(i, start, end)) != -1) {
			left = left(i, start, end);
			right = right(i, start, end);
			if (right == -1 || compare(arr[left], arr[right]) > 0) {
				arr[i] = arr[left];
				i = left;
			} else {
				arr[i] = arr[right];
				i = right;
			}
		}
		arr[i] = bubble;
		int p = parent(i, start, end);
		/*
		 * Sift up the node we just brought to the root until it is smaller than its parent.
		 */
		while (p >= 0) {
			if (compare(arr[i], arr[p]) > 0) {
				/* swap */
				T tmp = arr[i];
				arr[i] = arr[p];
				arr[p] = tmp;
			} else {
				break;
			}
			/* move up to parent */
			i = p;
			p = parent(p, start, end);
		}

	}

	/**
	 * Returns the left child of a given node, and if this child does not exist, -1.
	 * 
	 * @param index
	 *            The index of the parent node
	 * @param start
	 *            The lower bound of the heap inclusive
	 * @param end
	 *            The upper bound of the heap exclusive
	 * @return the left child of a given node, and if this child does not exist, -1
	 */
	private int left(int index, int start, int end) {
		int val = 2 * (index - start) + 1;
		return val >= end ? -1 : val;
	}

	/**
	 * Returns the right child of a given node, and if this child does not exist, -1.
	 * 
	 * @param index
	 *            The index of the parent node
	 * @param start
	 *            The lower bound of the heap inclusive
	 * @param end
	 *            The upper bound of the heap exclusive
	 * @return the right child of a given node, and if this child does not exist, -1
	 */
	private int right(int index, int start, int end) {
		int val = 2 * (index - start) + 2;
		return val >= end ? -1 : val;
	}

	/**
	 * Returns the parent of a given node, and if the node is the root, -1.
	 * 
	 * @param index
	 *            The index of the child node
	 * @param start
	 *            The lower bound of the heap inclusive
	 * @param end
	 *            The upper bound of the heap exclusive
	 * @return the parent of a given node, and if the node is the root, -1
	 */
	private int parent(int index, int start, int end) {
		return (index - start == 0) ? -1 : (int) ((index - 1 - start) / 2);
	}
}
