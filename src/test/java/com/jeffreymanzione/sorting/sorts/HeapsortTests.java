package com.jeffreymanzione.sorting.sorts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

public class HeapsortTests {

	@Test
	public void testSerial() {
		Sort<Integer> sort = new Heapsort<Integer>();

		List<Integer> ints = new ArrayList<Integer>();
		for (int i = 0; i < 100_000; i++) {
			ints.add(i);
		}

		Collections.shuffle(ints);

		Integer[] arr = new Integer[ints.size()];
		ints.toArray(arr);

		// System.out.println("Start");
		// long time = System.nanoTime();
		sort.sort(arr);
		// System.out.println(System.nanoTime() - time);
		// System.out.println(CommonTestMethods.getArrayToString(arr));
		Collections.sort(ints);
		List<Integer> result = Arrays.asList(arr);

		assertEquals(ints, result);

	}

	@Test
	public void testSortedSerial() throws SortIsNotParallelException {
		Sort<Integer> sort = new Quicksort<Integer>();

		CommonTestMethods.testSorted(sort, 1_000_000);
	}

	@Test(expected = SortIsNotParallelException.class)
	public void testSortedParallel() throws SortIsNotParallelException {
		Sort<Integer> sort = new Heapsort<Integer>();

		// System.out.println("Start");
		sort.setParallel(true);

		CommonTestMethods.testSorted(sort, 1_000_000);
	}

	@Test
	public void testReverseSortedSerial() throws SortIsNotParallelException {
		Sort<Integer> sort = new Heapsort<Integer>();

		CommonTestMethods.testReverseSorted(sort, 1_000_000);
	}

	@Test(expected = SortIsNotParallelException.class)
	public void testReverseSortedParallel() throws SortIsNotParallelException {
		Sort<Integer> sort = new Heapsort<Integer>();

		// System.out.println("Start");
		sort.setParallel(true);

		CommonTestMethods.testReverseSorted(sort, 1_000_000);
	}
}
