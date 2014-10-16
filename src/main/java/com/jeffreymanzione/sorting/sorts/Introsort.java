package com.jeffreymanzione.sorting.sorts;

import java.util.function.IntFunction;

import com.jeffreymanzione.sorting.AbstractSort;

/**
 * Introsort.java
 * 
 * An implementation of Introsort. Introsort is a combination of Quicksort and
 * Heapsort. Quicksort is performed until the specified recursion depth is
 * reached (default is the floor of 2n*log2(n)). Once the number of elements in
 * a partition less than 16, insertion sort is performed. If there are only 2 or
 * 3 elements, they are manually compared and swapped. Guarantees O(nlogn)
 * performance.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 31, 2014
 *
 */
public class Introsort<T extends Comparable<T>> extends AbstractSort<T> {

	/**
	 * The default scheme for calculating the recursion depth threshold. f(n) -
	 * {@literal >} floor(2nLog2(n)).
	 */
	public static final IntFunction<Integer>	Depth_2nLog2n			= n -> (int) Math.floor( 2 * n * Math.log( n )
																				/ Math.log( 2 ) );

	/* Private fields */
	private InsertionSort<T>					insertionSort;
	private Heapsort<T>							heapsort;
	private Quicksort<T>						quicksort;

	private IntFunction<Integer>				recursionDepthScheme;
	private int									recursionDepthThreshold	= -1;

	/**
	 * Default constructor. Uses 2n*Log2(n) as the default recursion depth
	 * scheme.
	 * 
	 * @see Introsort#Depth_2nLog2n
	 */
	public Introsort () {
		super();
		this.recursionDepthScheme = Depth_2nLog2n;
	}

	/**
	 * Constructor which sets the recursion depth scheme.
	 * 
	 * @param recursionDepthScheme
	 *            The function that should be used to calculate the recursion
	 *            depth threshold.
	 */
	public Introsort ( IntFunction<Integer> recursionDepthScheme ) {
		super();
		this.recursionDepthScheme = recursionDepthScheme;
	}

	/**
	 * Sets the recursion depth scheme.
	 * 
	 * @param recursionDepthScheme
	 *            The function that should be used to calculate the recursion
	 *            depth threshold.
	 */
	public void setRecursionDepthScheme ( IntFunction<Integer> recursionDepthScheme ) {
		this.recursionDepthScheme = recursionDepthScheme;
	}

	/**
	 * Gets the recursion depth scheme of this instance of Introsort.
	 * 
	 * @return The function that should be used to calculate the recursion depth
	 *         threshold.
	 */
	public IntFunction<Integer> getRecursionDepthScheme () {
		return this.recursionDepthScheme;
	}

	/**
	 * Resets the recursion depth scheme to the default.
	 * 
	 * @see #Depth_2nLog2n
	 */
	public void resetRecursionDepthSceme () {
		this.recursionDepthScheme = Depth_2nLog2n;
	}

	@Override
	public boolean isParallel () {
		return true;
	}

	@Override
	protected void sortImplementation ( T[] arr, int start, int end, int recursionDepth, int originalLength ) {
		if ( end - start > 1 ) {
			if ( this.recursionDepthThreshold == -1 ) {
				insertionSort = new InsertionSort<>();
				insertionSort.setComparator( this.getComparator() );

				heapsort = new Heapsort<>();
				heapsort.setComparator( this.getComparator() );

				quicksort = new Quicksort<>();
				quicksort.setComparator( this.getComparator() );

				this.recursionDepthThreshold = this.recursionDepthScheme.apply( end - start );
			}

			/*
			 * Insertion sort the range if there are 16 elements or fewer.
			 */
			if ( end - start < 16 ) {
				insertionSort.sortImplementation( arr, start, end, recursionDepth + 1, originalLength );
			}
			/*
			 * Heapsort if the recursion depth has reached the specified
			 * threshold.
			 */
			else if ( recursionDepth >= this.recursionDepthThreshold ) {
				heapsort.sortImplementation( arr, start, end, recursionDepth + 1, originalLength );
			}
			/*
			 * Otherwise use the partition method from quicksort and quicksort
			 * the elements.
			 */
			else {
				Range[] partitions = quicksort.partition( arr, start, end - 1, recursionDepth, originalLength );
				if ( partitions != null ) {
					Range range1 = partitions[0];

					if ( range1.end - range1.start > getParallelThreshold() ) {
						this.subsort( arr, range1.start, range1.end, recursionDepth + 1, originalLength );
					}
					if ( partitions.length > 1 ) {
						Range range2 = partitions[1];

						if ( range2.end - range2.start > getParallelThreshold() ) {
							this.subsort( arr, range2.start, range2.end, recursionDepth + 1, originalLength );
						} else {
							this.sortImplementation( arr, range2.start, range2.end, recursionDepth + 1, originalLength );
						}
					}

					if ( range1.end - range1.start <= getParallelThreshold() ) {
						this.sortImplementation( arr, range1.start, range1.end, recursionDepth + 1, originalLength );
					}

				}
			}
		}
	}

}
