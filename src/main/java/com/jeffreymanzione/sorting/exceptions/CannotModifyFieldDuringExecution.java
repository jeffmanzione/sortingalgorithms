package com.jeffreymanzione.sorting.exceptions;

/**
 * Exception which occurs when concurrent features of a sort are modified during the execution of a sort on that object.
 * 
 * @author Jeffrey J. Manzione
 * @version 0.1
 * @since August 24, 2014
 *
 */
public class CannotModifyFieldDuringExecution extends RuntimeException {
	private static final long serialVersionUID = 2875064559747567840L;

}
