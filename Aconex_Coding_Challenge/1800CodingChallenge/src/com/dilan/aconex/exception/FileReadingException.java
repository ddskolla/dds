package com.dilan.aconex.exception;

/**
 * Class Name : FileReadingException
 * 
 * Description : Custom exception thrown when there was a problem with reading either the
 * dictionary file or the numbers file.
 * 
 * @author Dilan
 */
public class FileReadingException extends Exception {

	public FileReadingException(String message) {
		super(message);
	}

}
