package com.jeffreymanzione.sorting.sorts;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;

public class InsertionSortTests {
	@Test
	public void testSerial() {
		for (int z = 0; z < 30; z++) {
			Sort<Integer> insertion = new InsertionSort<Integer>();

			List<Integer> ints = new ArrayList<Integer>();
			for (int i = 0; i < 10_000; i++) {
				ints.add(i);
			}

			Collections.shuffle(ints);

			Integer[] arr = new Integer[ints.size()];
			ints.toArray(arr);

			// System.out.println("Start");
			// long time = System.nanoTime();
			insertion.sort(arr);
			// System.out.println(System.nanoTime() - time);
			//System.out.println(CommonTestMethods.getArrayToString(arr));

			Collections.sort(ints);
			List<Integer> result = Arrays.asList(arr);

			assertEquals(ints, result);
		}

	}

}
