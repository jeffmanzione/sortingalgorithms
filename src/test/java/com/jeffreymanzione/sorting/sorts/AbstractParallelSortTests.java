package com.jeffreymanzione.sorting.sorts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

public abstract class AbstractParallelSortTests extends AbstractSortTests {
	
	protected List<Integer> ints;
	protected Integer[] testParallel, testReverseParallel, testSortedParallel;
	protected Sort<Integer> sortParallel, sortReverseParallel, sortSortedParallel;
	
	public AbstractParallelSortTests(Class<? extends Sort<Integer>> sortClass, int size) {
		super(sortClass, size);
	}

	@Override
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		super.setUp();
		
		List<Integer> testList = new ArrayList<>();
		testParallel = new Integer[size];
		testReverseParallel = new Integer[size];
		testSortedParallel = new Integer[size];
		
		sortParallel = sortClass.newInstance();
		sortParallel.setParallel(true);
		sortReverseParallel = sortClass.newInstance();
		sortReverseParallel.setParallel(true);
		sortSortedParallel = sortClass.newInstance();
		sortSortedParallel.setParallel(true);
		
		ints = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints.add(i);
			testList.add(i);
			testSortedParallel[i] = i;
			testReverseParallel[size - i - 1] = i;
		}
		Collections.shuffle(testList);
		testParallel = testList.toArray(testParallel);
		
	}
	
	@Test
	public void testParallel() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		sortParallel.sort(testParallel);
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
