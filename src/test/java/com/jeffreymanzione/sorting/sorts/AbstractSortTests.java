package com.jeffreymanzione.sorting.sorts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;

public abstract class AbstractSortTests {

	Class<? extends Sort<Integer>> sortClass;

	protected static final int size = 100_000;

	public AbstractSortTests(Class<? extends Sort<Integer>> sortClass) {
		this.sortClass = sortClass;
	}

	/**
	 * 
	 * @return The string representation of the array.
	 */
	public static <T> String getArrayToString(T[] arr) {
		String result = "[" + arr[0];
		for (int i = 1; i < arr.length; i++) {
			result += "," + arr[i];
		}
		return result + "]";
	}

	public static void check(List<Integer> elts, Integer[] shouldBeSorted) {
		Collections.sort(elts);
		List<Integer> result = Arrays.asList(shouldBeSorted);

		assertEquals(elts, result);
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		Sort<Integer> sort = sortClass.newInstance();

		List<Integer> ints = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints.add(i);
		}

		Collections.shuffle(ints);

		Integer[] arr = new Integer[ints.size()];
		ints.toArray(arr);

		sort.sort(arr);
		check(ints, arr);

	}

	@Test
	public void testSorted() throws InstantiationException, IllegalAccessException {
		Sort<Integer> sort = sortClass.newInstance();

		Integer[] ints = new Integer[size];
		List<Integer> checker = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints[i] = i;
			checker.add(i);
		}

		sort.sort(ints);
		check(checker, ints);
	}

	@Test
	public void testReverseSorted() throws InstantiationException, IllegalAccessException {
		Sort<Integer> sort = sortClass.newInstance();

		Integer[] ints = new Integer[size];
		List<Integer> checker = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints[i] = size - 1 - i;
			checker.add(size - 1 - i);
		}

		sort.sort(ints);
		check(checker, ints);
	}
}
