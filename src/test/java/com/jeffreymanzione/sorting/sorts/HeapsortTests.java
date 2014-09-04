package com.jeffreymanzione.sorting.sorts;


import com.jeffreymanzione.sorting.Sort;

public class HeapsortTests extends AbstractSortTests {

	@SuppressWarnings("unchecked")
	public HeapsortTests() {
		super((Class<? extends Sort<Integer>>) Heapsort.class);
	}
}
