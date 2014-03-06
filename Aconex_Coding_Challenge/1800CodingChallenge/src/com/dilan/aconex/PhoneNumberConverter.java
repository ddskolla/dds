package com.dilan.aconex;

import java.util.List;
import java.util.Map;

import com.dilan.aconex.api.ResultHolder;
import com.dilan.aconex.exception.FileReadingException;
import com.dilan.aconex.util.Constants;
import com.dilan.aconex.util.DataProcessor;
import com.dilan.aconex.util.DictionaryProcessor;
import com.dilan.aconex.util.NumberListProcessor;
import com.dilan.aconex.util.OutputProcessor;

/**
 * Class Name : PhoneNumberConverter
 * 
 * Description : This is the main class of the 1800CodingChallenge. This class
 * validates the command line user inputs and then call the other utility
 * classes to produce the required output to the user.
 * 
 * @author Dilan
 */
public class PhoneNumberConverter {

	//Utility classes used to process the data files.
	static DictionaryProcessor dictionaryProcessor = new DictionaryProcessor();
	static NumberListProcessor numberListProcessor = new NumberListProcessor();

	public static void main(String[] args) {

		// Validating the command line arguments as well as setting the
		// -d parameter, if it's provided.
		String dictionaryLocation = null, phoneNumberListLocation = null;

		if (args.length == 1) {
			dictionaryLocation = Constants.defaultDictionaryLocation;
			phoneNumberListLocation = args[0];
		} else if (args.length == 3
				&& args[0].equalsIgnoreCase(Constants.setDictionarySymbol)) {
			dictionaryLocation = args[1];
			phoneNumberListLocation = args[2];
		} else {
			System.out.println(Constants.userInformationString);
			Runtime.getRuntime().exit(0);
		}

		Map<String, List<String>> dictionaryMap;
		List<String> phoneNumberList;
		List<ResultHolder> results;
		DataProcessor dataProcessor = new DataProcessor();
		OutputProcessor outputProcessor = new OutputProcessor();

		try {
			// Processing the dictionary to a more friendly map.
			dictionaryMap = dictionaryProcessor
					.processDictionary(dictionaryLocation);

			// Processing the number list to a array list.
			phoneNumberList = numberListProcessor
					.processPhoneNumbers(phoneNumberListLocation);

			// Processing all the data and making sense of everything.
			results = dataProcessor.processData(dictionaryMap, phoneNumberList);

			// Printing the final output to the user.
			outputProcessor.printOutput(results);

		} catch (FileReadingException e) {
			System.out.println(e.getMessage());
		}

	}
}