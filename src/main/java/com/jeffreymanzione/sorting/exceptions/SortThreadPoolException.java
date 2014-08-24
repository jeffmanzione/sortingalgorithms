package com.jeffreymanzione.sorting.exceptions;

public class SortThreadPoolException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -28097791230456017L;

	
	private Exception e;
	
	public SortThreadPoolException(Exception e) {
		this.e = e;
	}
	
	public Exception getWrappedException() {
		return e;
	}
}
