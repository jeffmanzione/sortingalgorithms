package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.Sort;

public class QuicksortTests extends AbstractParallelSortTests {

	@SuppressWarnings("unchecked")
	public QuicksortTests() {
		super((Class<? extends Sort<Integer>>) Quicksort.class);
	}

}
