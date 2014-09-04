package com.jeffreymanzione.sorting.sorts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

public abstract class AbstractParallelSortTests extends AbstractSortTests {

	public AbstractParallelSortTests(Class<? extends Sort<Integer>> sortClass) {
		super(sortClass);
	}

	@Test
	public void testParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		Sort<Integer> sort = sortClass.newInstance();

		List<Integer> ints = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints.add(i);
		}

		Collections.shuffle(ints);

		Integer[] arr = new Integer[ints.size()];
		ints.toArray(arr);

		sort.setParallel(true);
		sort.sort(arr);
		check(ints, arr);

	}

	@Test
	public void testSortedParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		Sort<Integer> sort = sortClass.newInstance();

		Integer[] ints = new Integer[size];
		List<Integer> checker = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints[i] = i;
			checker.add(i);
		}

		sort.setParallel(true);
		sort.sort(ints);
		check(checker, ints);
	}

	@Test
	public void testReverseSortedParallel() throws InstantiationException, IllegalAccessException,
			SortIsNotParallelException {
		Sort<Integer> sort = sortClass.newInstance();

		Integer[] ints = new Integer[size];
		List<Integer> checker = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints[i] = size - 1 - i;
			checker.add(size - 1 - i);
		}

		sort.setParallel(true);
		sort.sort(ints);
		check(checker, ints);
	}
}
