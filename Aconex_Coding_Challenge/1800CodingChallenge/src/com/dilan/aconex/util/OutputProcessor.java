package com.dilan.aconex.util;

import java.util.List;

import com.dilan.aconex.api.MatchResults;
import com.dilan.aconex.api.ResultHolder;

/**
 * Class Name : OutputProcessor
 * 
 * Description : Gets the list of ResultHolder objects and then prints out the
 * data in a meaningful format.
 * 
 * @author Dilan
 */
public class OutputProcessor {

	public void printOutput(List<ResultHolder> results) {

		for (ResultHolder result : results) {

			System.out.println(Constants.outputLine);
			System.out.println(Constants.phoneNumberString
					+ result.getPhoneNumber().replaceAll(
							Constants.phoneNumberChunkSeperatorString,
							Constants.outputNumberSeperator));
			System.out.println(Constants.resultsPrompt);
			System.out.println(Constants.outputLine);

			for (MatchResults match : result.getSuggestions()) {

				System.out.print(match.getPart() + " : ");

				if (match.isIsmatchFound()) {

					System.out.println(match.getMatches().toString());

				} else {

					System.out.println(Constants.noMatchesFoundMEssage);

				}

			}

			System.out.println(Constants.outputLine);
		}
	}

}
