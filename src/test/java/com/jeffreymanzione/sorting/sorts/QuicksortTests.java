package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.ParallelSort;

public class QuicksortTests extends AbstractParallelSortTests {

	@SuppressWarnings("unchecked")
	public QuicksortTests() {
		super((Class<? extends ParallelSort<Integer>>) Quicksort.class, 1_000_000);
	}

}
