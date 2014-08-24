package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.AbstractSort;

public class Heapsort<T extends Comparable<T>> extends AbstractSort<T> {

	@Override
	public boolean isParallel() {
		return false;
	}

	@Override
	protected void sortImplementation(T[] arr, int start, int end) {
			
	}

	private void heapify(T[] arr, int start, int end) {

	}

	private void siftUp(T[] arr, int start, int end) {

	}

	private int left(int index, int start) {
		return 2 * (index - start) + 1;
	}

	private int right(int index, int start) {
		return 2 * (index - start) + 2;
	}

	private int parent(int index, int start) {
		return (int) ((index - 1 - start) / 2);
	}
}
