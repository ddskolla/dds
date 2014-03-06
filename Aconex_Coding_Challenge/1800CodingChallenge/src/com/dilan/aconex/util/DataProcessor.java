package com.dilan.aconex.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dilan.aconex.api.MatchResults;
import com.dilan.aconex.api.ResultHolder;

/**
 * Class Name : DataProcessor
 * 
 * Description : When a dictionary map and a phone number list is provided, this class will
 * check if the [provided numbers/or their parts] match any words from the
 * dictionary and then populates and returns the list of ResultHolder objects.
 * 
 * @author Dilan
 */
public class DataProcessor {

	public List<ResultHolder> processData(
			Map<String, List<String>> dictionaryMap,
			List<String> phoneNumberList) {

		List<ResultHolder> results = new ArrayList<ResultHolder>();

		for (String inputNumber : phoneNumberList) {

			String[] parts = inputNumber
					.split(Constants.phoneNumberChunkSeperatorString);

			ResultHolder result = new ResultHolder();
			result.setPhoneNumber(inputNumber);
			result.setNumberOfParts(parts.length);

			for (String part : parts) {

				MatchResults matchResults = new MatchResults();
				matchResults.setPart(part);
				matchResults.setIsmatchFound(false);
				List<String> matchingWords = dictionaryMap.get(part);

				if ((matchingWords != null) && (!matchingWords.isEmpty())) {

					matchResults.setIsmatchFound(true);

					for (String match : matchingWords) {

						matchResults.addMatches(match);

					}

				}
				result.addSuggestions(matchResults);
			}

			results.add(result);

		}

		return results;

	}

}
