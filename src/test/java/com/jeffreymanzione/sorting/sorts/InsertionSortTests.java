package com.jeffreymanzione.sorting.sorts;


import com.jeffreymanzione.sorting.Sort;

public class InsertionSortTests extends AbstractSortTests {
	@SuppressWarnings("unchecked")
	public InsertionSortTests() {
		super((Class<? extends Sort<Integer>>) InsertionSort.class, 100_000);
	}
}
