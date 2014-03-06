package com.dilan.aconex.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.dilan.aconex.exception.FileReadingException;

/**
 * Class Name : NumberListProcessor
 * 
 * Description : Reads the specified file and returns a list of phone numbers
 * that are to be processed later.
 * 
 * @author Dilan
 */

public class NumberListProcessor {

	private List<String> phoneNumbers = new ArrayList<String>();

	public List<String> processPhoneNumbers(String fileLocation)
			throws FileReadingException {

		BufferedReader buffer = null;
		String tempString;

		try {

			buffer = new BufferedReader(new FileReader(fileLocation));

			while ((tempString = buffer.readLine()) != null) {

				phoneNumbers.add(cleanAndFormatNumber(tempString));

			}

		} catch (IOException e) {

			throw new FileReadingException(Constants.phoneNumberReadError);

		} finally {
			try {
				if (buffer != null)
					buffer.close();
			} catch (IOException ex) {
				throw new FileReadingException(Constants.phoneNumberReadError);
			}
		}

		return phoneNumbers;

	}

	private String cleanAndFormatNumber(String number) {

		String cleanString = number.replaceAll(Constants.phoneNumberCleanRegEx,
				Constants.phoneNumberChunkSeperatorString).trim();

		if (cleanString.length() > 0
				&& cleanString.charAt(0) == Constants.phoneNumberChunkSeperatorChar) {

			cleanString = cleanString.substring(1, cleanString.length());

		}

		return cleanString;
	}
}
