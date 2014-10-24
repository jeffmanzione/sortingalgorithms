package com.jeffreymanzione.sorting.sorts;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.jeffreymanzione.sorting.ParallelSort;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

public abstract class AbstractParallelSortTests extends AbstractSortTests {

	protected Integer[] testParallel, testReverseParallel, testSortedParallel;
	protected ParallelSort<Integer> sortParallel, sortReverseParallel, sortSortedParallel;

	protected Class<? extends ParallelSort<Integer>> parallelSortClass;

	public AbstractParallelSortTests(Class<? extends ParallelSort<Integer>> sortClass, int size) {
		super(sortClass, size);
		this.parallelSortClass = sortClass;
	}

	@Override
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		super.setUp();

		testParallel = Arrays.copyOf(super.test, size);
		testReverseParallel = Arrays.copyOf(super.testReverse, size);
		testSortedParallel = Arrays.copyOf(super.testSorted, size);

		sortParallel = parallelSortClass.newInstance();
		sortReverseParallel = parallelSortClass.newInstance();
		sortSortedParallel = parallelSortClass.newInstance();

	}

	@Test
	public void testParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		System.out.println(this.getClass().getSimpleName().toString() + ": "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		long time = System.currentTimeMillis();
		sortParallel.sortParallel(testParallel);
		sortParallel.awaitCompletion();
		time = System.currentTimeMillis() - time;
		System.out.println("Time: " + ((double) time) / 1_000 + " ms");
		check(ints, testParallel);

	}

	@Test
	public void testSortedParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		System.out.println(this.getClass().getSimpleName().toString() + ": "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		long time = System.currentTimeMillis();
		sortSortedParallel.sortParallel(testSortedParallel);
		sortParallel.awaitCompletion();
		time = System.currentTimeMillis() - time;
		System.out.println("Time: " + ((double) time) / 1_000 + " ms");
		check(ints, testSortedParallel);
	}

	@Test
	public void testReverseSortedParallel() throws InstantiationException, IllegalAccessException,
			SortIsNotParallelException {
		System.out.println(this.getClass().getSimpleName().toString() + ": "
				+ Thread.currentThread().getStackTrace()[1].getMethodName());

		long time = System.currentTimeMillis();
		sortReverseParallel.sortParallel(testReverseParallel);
		sortParallel.awaitCompletion();
		time = System.currentTimeMillis() - time;
		System.out.println("Time: " + ((double) time) / 1_000 + " ms");
		check(ints, testReverseParallel);
	}
}
