package com.dilan.aconex.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dilan.aconex.exception.FileReadingException;

/**
 * Class Name : DictionaryProcessor
 * 
 * Description : Reads the specified file and returns a map of encoded phone numbers and a
 * list of words corresponding to that number.
 * 
 * @author Dilan
 */
public class DictionaryProcessor {

	private Map<String, List<String>> dictionary = new HashMap<String, List<String>>();

	public Map<String, List<String>> processDictionary(String fileLocation)
			throws FileReadingException {

		BufferedReader buffer = null;
		String tempString;

		try {

			buffer = new BufferedReader(new FileReader(fileLocation));

			while ((tempString = buffer.readLine()) != null) {

				storeWord(tempString.toUpperCase());

			}

		} catch (IOException e) {

			throw new FileReadingException(
					Constants.dictionaryReadError);

		} finally {
			try {
				if (buffer != null)
					buffer.close();
			} catch (IOException ex) {
				throw new FileReadingException(
						Constants.dictionaryReadError);
			}
		}

		return dictionary;

	}

	public String convert(String word) {
		StringBuilder sb = new StringBuilder();

		for (char c : word.toLowerCase().toCharArray()) {
			int i = Constants.chars.indexOf(c);
			if (i >= 0)
				sb.append(Constants.encoded.toCharArray()[i]);
		}

		return sb.toString();
	}

	public void storeWord(String word) {

		String enc = convert(word);
		List<String> value = dictionary.get(enc);

		if (value == null)
			dictionary.put(enc, value = new ArrayList<String>());

		value.add(word);
	}

}
