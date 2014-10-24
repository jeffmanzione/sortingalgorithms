package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.ParallelSort;

public class IntrosortTests extends AbstractParallelSortTests {

	@SuppressWarnings("unchecked")
	public IntrosortTests() {
		super((Class<? extends ParallelSort<Integer>>) Introsort.class, 1_000_000);
	}

}
