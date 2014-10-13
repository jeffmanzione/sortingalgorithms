package com.jeffreymanzione.sorting.sorts;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jeffreymanzione.sorting.Sort;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

public abstract class AbstractSortTests {

	Class<? extends Sort<Integer>> sortClass;
	
	protected List<Integer> ints;
	protected Integer[] test, testReverse, testSorted;
	protected Sort<Integer> sort, sortReverse, sortSorted;
	
	/**
	 * Default number of elements for the test.
	 */
	protected final int size;

	/**
	 * The compiler is throwing a fit over the casting to "Class<? extends Sort<Integer>>". This is a know bug. See:
	 * 
	 * @see <a
	 *      href="http://stackoverflow.com/questions/5633424/is-it-an-eclipse-or-maven-compiler-plugin-bug-the-generics-class-cast-issue">StackOverflow</a>
	 * 
	 * @param sortClass
	 * @param size
	 */
	public AbstractSortTests(Class<? extends Sort<Integer>> sortClass, int size) {
		this.sortClass = sortClass;
		this.size = size;
	}

	/**
	 * Set up the test by generating the necessary lists before hand. This way the time for building the lists are not
	 * included in the tests.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@Before
	public void setUp() throws InstantiationException, IllegalAccessException, SortIsNotParallelException {
		List<Integer> testList = new ArrayList<>();
		test = new Integer[size];
		testReverse = new Integer[size];
		testSorted = new Integer[size];
		
		sort = sortClass.newInstance();
		sortReverse = sortClass.newInstance();
		sortSorted = sortClass.newInstance();
		
		ints = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			ints.add(i);
			testList.add(i);
			testSorted[i] = i;
			testReverse[size - i - 1] = i;
		}
		Collections.shuffle(testList);
		test = testList.toArray(test);
	}

	/**
	 * This may be useful one day.
	 */
	@After
	public void tearDown() {

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
		List<Integer> result = Arrays.asList(shouldBeSorted);
		assertEquals(elts, result);
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		sort.sort(test);
		check(ints, test);
	}

	@Test
	public void testSorted() throws InstantiationException, IllegalAccessException {
		sortSorted.sort(testSorted);
		check(ints, testSorted);
	}

	@Test
	public void testReverseSorted() throws InstantiationException, IllegalAccessException {
		sortReverse.sort(testReverse);
		check(ints, testReverse);
	}
}
