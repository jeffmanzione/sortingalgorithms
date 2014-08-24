package com.jeffreymanzione.sorting.sorts;

public class CommonTestMethods {
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
}
