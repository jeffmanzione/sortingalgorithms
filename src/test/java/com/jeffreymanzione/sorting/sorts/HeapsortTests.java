package com.jeffreymanzione.sorting.sorts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;

public class HeapsortTests {

	@Test
	public void testSerial() {
		Sort<Integer> sort = new Heapsort<Integer>();

		List<Integer> ints = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			ints.add(i);
		}

		Collections.shuffle(ints);

		Integer[] arr = new Integer[ints.size()];
		ints.toArray(arr);

		// System.out.println("Start");
		// long time = System.nanoTime();
		sort.sort(arr);
		// System.out.println(System.nanoTime() - time);
		System.out.println(CommonTestMethods.getArrayToString(arr));

		Collections.sort(ints);
		List<Integer> result = Arrays.asList(arr);

		assertEquals(ints, result);

	}
}
