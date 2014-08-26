package com.jeffreymanzione.sorting.exceptions;

import com.jeffreymanzione.sorting.Sort;

/**
 * Exception when parallel features are being accessed on a sort which lacks parallel implementation.
 * @see Sort#isParallel()
 * @see Sort#setParallel(boolean)
 * @see Sort#setParallelThreshold(int)
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 24, 2014
 *
 */
public class SortIsNotParallelException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5198282972764787939L;

}
