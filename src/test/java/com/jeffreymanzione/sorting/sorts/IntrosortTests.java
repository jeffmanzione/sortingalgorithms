package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.Sort;

public class IntrosortTests extends AbstractParallelSortTests {

	@SuppressWarnings("unchecked")
	public IntrosortTests() {
		super((Class<? extends Sort<Integer>>) Introsort.class);
	}

}
