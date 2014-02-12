package com.dilan.rea.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.dilan.rea.exception.InvalidInputDataException;

/**
 * Reads the specified file and returns the data as a string separated by ":".
 * @author Dilan
 *
 */
public class InputReader {

	public String readInputFile(String fileLocation)
			throws InvalidInputDataException {

		BufferedReader buffer = null;
		String tempString;
		StringBuilder sCurrentLine = new StringBuilder();

		try {
			buffer = new BufferedReader(new FileReader(fileLocation));

			while ((tempString = buffer.readLine()) != null) {
				
				sCurrentLine.append(tempString+":");

			}

		} catch (IOException e) {

			System.out.println(e);
			throw new InvalidInputDataException(
					"Problem reading input file, Please check and try again");
		} finally {
			try {
				if (buffer != null)
					buffer.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return sCurrentLine.toString();

	}

}
