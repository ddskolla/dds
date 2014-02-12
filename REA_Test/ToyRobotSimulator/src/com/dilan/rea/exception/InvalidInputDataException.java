package com.dilan.rea.exception;

/**
 * Custom exception thrown when the input data is malformed.
 * @author Dilan
 *
 */
public class InvalidInputDataException extends Exception {

	public InvalidInputDataException(String message) {
		super(message);
	}

}
