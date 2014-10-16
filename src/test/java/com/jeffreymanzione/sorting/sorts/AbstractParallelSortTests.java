package com.jeffreymanzione.sorting.sorts;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

public abstract class AbstractParallelSortTests extends AbstractSortTests {

	protected Integer[] testParallel, testReverseParallel, testSortedParallel;
	protected Sort<Integer> sortParallel, sortReverseParallel, sortSortedParallel;
	
	public AbstractParallelSortTests(Class<? extends Sort<Integer>> sortClass, int size) {
		super(sortClass, size);
	}

	@Override
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		super.setUp();
		
		testParallel = Arrays.copyOf( super.test, size );
		testReverseParallel = Arrays.copyOf( super.testReverse, size );
		testSortedParallel = Arrays.copyOf( super.testSorted, size );
		
		sortParallel = sortClass.newInstance();
		sortParallel.setParallel(true);
		sortReverseParallel = sortClass.newInstance();
		sortReverseParallel.setParallel(true);
		sortSortedParallel = sortClass.newInstance();
		sortSortedParallel.setParallel(true);
		
	}
	
	@Test
	public void testParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		long time = System.currentTimeMillis();
		sort.sort(test);
		sortParallel.sort(testParallel);
		time = System.currentTimeMillis() - time;
		System.out.println("Test: " + ((double) time) / 1_000 + " ms");
		check(ints, testParallel);

	}

	@Test
	public void testSortedParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		sortSortedParallel.sort(testSortedParallel);
		check(ints, testSortedParallel);
	}

	@Test
	public void testReverseSortedParallel() throws InstantiationException, IllegalAccessException,
			SortIsNotParallelException {
		sortReverseParallel.sort(testReverseParallel);
		check(ints, testReverseParallel);
	}
}
