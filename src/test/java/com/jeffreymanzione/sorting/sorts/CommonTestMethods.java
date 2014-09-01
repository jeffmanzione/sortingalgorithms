package com.jeffreymanzione.sorting.sorts;

import com.jeffreymanzione.sorting.Sort;

public class CommonTestMethods {
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

	public static void testSorted(Sort<Integer> sort, int size) {
		Integer[] ints = new Integer[size];
		for (int i = 0; i < size; i++) {
			ints[i] = i;
		}

		long time = System.nanoTime();
		sort.sort(ints);
		System.out.println(System.nanoTime() - time);

	}

	public static void testReverseSorted(Sort<Integer> sort, int size) {
		Integer[] ints = new Integer[size];
		for (int i = 0; i < size; i++) {
			ints[i] = size - 1 - i;
		}

		//long time = System.nanoTime();
		sort.sort(ints);
		//System.out.println(System.nanoTime() - time);

	}
}
