package com.jeffreymanzione.sorting;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * AbstractSort.java
 * 
 * An abstract implementation of <b>ParallelSort</b>. Implements the core functionality and helper methods of a
 * ParallelSort. Provides the thread pool for parallel sorts via {@link #subsort(Comparable[], int, int, int, int)}
 * which creates a task for specified sort and pushes it on the thread pool queue. Parallel sorts will use the number of
 * threads equal to the number of available processors as returned {@link Runtime#availableProcessors()}.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since October 23, 2014
 * @see Sort
 * @param <T>
 *            The parameterized type
 */
public abstract class AbstractParallelSort<T extends Comparable<T>> extends AbstractSort<T> implements ParallelSort<T> {

	/*
	 * Parallel fields
	 */
	private ExecutorService executor;
	private List<Future<?>> futures;
	private int parallelThreshold;

	
	protected AbstractParallelSort() {
		super();
		futures = new Vector<>();
		parallelThreshold = ParallelSort.DEFAULT_PARALLEL_THRESHOLD;
		executor = Executors.newCachedThreadPool();
	}

	/**
	 * Runnable task to be passed to and executed by the internal thread pool.
	 * 
	 * @author Jeffrey J. Manzione
	 *
	 */
	private class Subsort implements Runnable {

		private AbstractSort<T> sort;
		private T[] arr;
		private int start, end, recursionDepth, originalLength;

		public Subsort(AbstractSort<T> sort, T[] arr, int start, int end, int recursionDepth, int originalLength) {
			this.sort = sort;
			this.arr = arr;
			this.start = start;
			this.end = end;
			this.recursionDepth = recursionDepth;
			this.originalLength = originalLength;
		}

		@Override
		public void run() {
			sort.sortImplementation(arr, start, end, recursionDepth, originalLength);
		}
	}

	@Override
	public void sortParallel(T[] arr, int start, int end) {
		super.sort(arr, start, end);
	}

	@Override
	public void sortParallel(T[] arr) {
		sortParallel(arr, 0, arr.length);
	}

	/**
	 * Adds a range of an array to be sorted onto the thread pool queue. The sort specified can be the parent sort
	 * instance or can be any other non-interfering instance.
	 * 
	 * @param sort
	 *            Sort to sort the range
	 * @param arr
	 *            The source array to sort
	 * @param start
	 *            The start of the range inclusive
	 * @param end
	 *            The end of the range exclusive
	 * @param recursionDepth
	 *            The number of recurrences of the sort before this iteration.
	 * @param originalLength
	 *            The original length of the array to be sorted.
	 * @param isParallel
	 *            Is the sort parallel?
	 */
	protected void subsort(AbstractSort<T> sort, T[] arr, int start, int end, int recursionDepth, int originalLength,
			boolean isParallel) {
		if (isParallel) {
			futures.add(executor.submit(new Subsort(sort, arr, start, end, recursionDepth, originalLength)));
		} else {
			this.sortImplementation(arr, start, end, recursionDepth, originalLength, isParallel);
		}
	}

	/**
	 * Adds a range of an array to be sorted onto the thread pool queue.
	 * 
	 * @param arr
	 *            The source array to sort
	 * @param start
	 *            The start of the range inclusive
	 * @param end
	 *            The end of the range exclusive
	 * @param recursionDepth
	 *            The number of recurrences of the sort before this iteration.
	 * @param originalLength
	 *            The original length of the array to be sorted.
	 * @param isParallel
	 *            Is the sort parallel?
	 */
	protected void subsort(T[] arr, int start, int end, int recursionDepth, int originalLength, boolean isParallel) {
		this.subsort(this, arr, start, end, recursionDepth, originalLength, isParallel);
	}

	/**
	 * The implementation of the parallel sort which is indirectly called by {@link #sort(Comparable[])} and
	 * {@link #sort(Comparable[], int, int)}. The proper implementation of this function should uphold the contract that
	 * it sorts the list using the {@link #compare(Comparable, Comparable)} method. There is no built-in check as to
	 * whether the sort successfully sorted the elements in the specified range. The implementation is not required to
	 * block the return of this method in the case of pending subsorts as {@link #sort(Comparable[])} and
	 * {@link #sort(Comparable[], int, int)} will wait for the completion of all pending subsorts before returning.
	 * 
	 * @param arr
	 *            The array to be sorted
	 * @param start
	 *            The starting index of the portion of the array to be sorted inclusive
	 * @param end
	 *            The ending index of the portion of the array to be sorted exclusive
	 * @param recursionDepth
	 *            the number of recursion on this sort before this iteration of sorting is called. (This is for sorts
	 *            where recursion depth matters such as with Introsort)
	 * @param originalLength
	 *            The original length of the array to be sorted.
	 * @param isParallel
	 *            Is the sort parallel?
	 * 
	 */
	protected abstract void sortImplementation(T[] arr, int start, int end, int recursionDepth, int originalLength,
			boolean isParallel);

	@Override
	protected void sortImplementation(T[] arr, int start, int end, int recursionDepth, int originalLength) {
		this.sortImplementation(arr, start, end, recursionDepth, originalLength, false);
	}

	@Override
	public void setParallelThreshold(int minNumElements) {
		// if (setParallelLock.tryLock()) {
		// try {
		parallelThreshold = minNumElements;
		// } finally {
		// setParallelLock.unlock();
		// }
		// } else {
		// throw new CannotModifyFieldDuringExecutionException();
		// }

	}

	@Override
	public int getParallelThreshold() {
		return parallelThreshold;
	}

	@Override
	public void awaitCompletion() {
		while (!futures.isEmpty()) {
			try {
				futures.remove(0).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}
