package com.jeffreymanzione.sorting;

import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jeffreymanzione.sorting.exceptions.CannotModifyFieldDuringExecution;
import com.jeffreymanzione.sorting.exceptions.SortIsNotParallelException;

/**
 * AbstractSort.java
 * 
 * An abstract implementation of <b>Sort</b>. Implements the core functionality and helper methods of a Sort. Provides
 * the thread pool for parallel sorts via {@link #subsort(Comparable[], int, int)} which creates a task for specified
 * sort and pushes it on the thread pool queue.
 * 
 * @author Jeff
 * @version 0.1
 * @since August 24, 2014
 *
 * @param <T>
 *            The parameterized type
 */
public abstract class AbstractSort<T extends Comparable<T>> implements Sort<T> {

	/*
	 * Comparators fields
	 */
	private Comparator<T> comparator;
	private boolean hasComparator;

	/*
	 * Parallel fields
	 */
	private boolean isParallel;
	private ExecutorService executor;
	private List<Future<?>> futures;
	private int parallelThreshold;
	private Lock setParallelLock;

	protected AbstractSort() {
		hasComparator = false;
		isParallel = false;
		setParallelLock = new ReentrantLock();
		futures = new Vector<>();
		parallelThreshold = Sort.DEFAULT_PARALLEL_THRESHOLD;
	}

	protected AbstractSort(Comparator<T> comparator) {
		this();
		this.setComparator(comparator);
	}

	/**
	 * Compares the specified elements. Works the same as {@link Comparator#compare(Object, Object)}. The purpose is to
	 * abstract the comparator from the developer.
	 * 
	 * @param t1
	 *            The first element to be sorted
	 * @param t2
	 *            The second element t
	 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater
	 *         than the second.
	 */
	protected int compare(T t1, T t2) {
		if (hasComparator) {
			return comparator.compare(t1, t2);
		} else {
			return t1.compareTo(t2);
		}
	}

	@Override
	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
		hasComparator = true;
	}

	@Override
	public Comparator<T> getComparator() {
		return comparator;
	}

	@Override
	public boolean removeComparator() {
		if (hasComparator) {
			this.comparator = null;
			hasComparator = false;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void sort(T[] arr) {
		sort(arr, 0, arr.length);

	}

	@Override
	public void sort(T[] arr, int start, int end) {
		parallelSortWrapperForWaiting(arr, start, end);
	}

	private void parallelSortWrapperForWaiting(T[] arr, int start, int end) {
		if (isParallel) {
			setParallelLock.lock();
		}
		subsort(arr, start, end);
		if (isParallel) {
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

		if (isParallel) {
			setParallelLock.unlock();
		}
	}

	/**
	 * The implementation of the sort which is indirectly called by {@link #sort(Comparable[])} and
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
	 */
	protected abstract void sortImplementation(T[] arr, int start, int end);

	/**
	 * Runnable task to be passed to and executed by the internal thread pool.
	 * 
	 * @author Jeff
	 *
	 */
	private class Subsort implements Runnable {

		private AbstractSort<T> sort;
		private T[] arr;
		private int start, end;

		public Subsort(AbstractSort<T> sort, T[] arr, int start, int end) {
			this.sort = sort;
			this.arr = arr;
			this.start = start;
			this.end = end;
		}

		@Override
		public void run() {
			// System.out.println("Sorting: " + start + " to " + end);
			sort.sortImplementation(arr, start, end);
		}
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
	 */
	protected void subsort(AbstractSort<T> sort, T[] arr, int start, int end) {
		if (isParallel) {
			futures.add(executor.submit(new Subsort(sort, arr, start, end)));
		} else {
			sort.sortImplementation(arr, start, end);
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
	 */
	protected void subsort(T[] arr, int start, int end) {
		subsort(this, arr, start, end);
	}

	@Override
	public void setParallel(boolean isParallel) throws SortIsNotParallelException {
		if (this.isParallel()) {
			if (setParallelLock.tryLock()) {
				try {
					this.isParallel = isParallel;
					if (executor == null) {
						executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
					}
				} finally {
					setParallelLock.unlock();
				}
			} else {
				throw new CannotModifyFieldDuringExecution();
			}
		} else {
			throw new SortIsNotParallelException();
		}
	}

	@Override
	public void setParallelThreshold(int minNumElements) throws SortIsNotParallelException {
		if (isParallel()) {
			if (setParallelLock.tryLock()) {
				try {
					parallelThreshold = minNumElements;
				} finally {
					setParallelLock.unlock();
				}
			} else {
				throw new CannotModifyFieldDuringExecution();
			}
		} else {
			throw new SortIsNotParallelException();
		}
	}

	@Override
	public int getParallelThreshold() {
		if (isParallel()) {
			return parallelThreshold;
		} else {
			return 0;
		}
	}

	//
	// protected void lock() {
	// lock.lock();
	// }
	//
	// protected void unlock() {
	// lock.unlock();
	// }
}
