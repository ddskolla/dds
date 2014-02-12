package com.dilan.rea;

import java.util.ArrayList;

import com.dilan.rea.api.CustomDate;
import com.dilan.rea.exception.InvalidInputException;
import com.dilan.rea.util.DateValidator;
import com.dilan.rea.util.InputReader;

public class DateDifferenceCalculator {

	public static void main(String[] args) {

		//Reads the input from the file
		InputReader inputReader = new InputReader();
		//Used to validate the input data
		DateValidator validator = new DateValidator();

		try {
			String inputString = inputReader
					.readInputFile("C:\\dev\\REA_Test\\DateDifferenceCalculator\\InputData.txt");
			String[] datePairs = inputString.split(":");

			//Looping through the input dates and printing out the date dfference
			for (int a = 0; a < datePairs.length; a++) {

				String[] datesArray = validator
						.validateInputString(datePairs[a]);
				ArrayList<CustomDate> validatedDates = validator
						.validateAndPopulateDates(datesArray);
				CustomDate dateOne = validatedDates.get(0);
				CustomDate dateTwo = validatedDates.get(1);

				//Adding one day to the final result because of how dates are being calculated.
				System.out.println(dateTwo.getDate() + " " + dateTwo.getMonth()
						+ " " + dateTwo.getYear() + " , " + dateOne.getDate()
						+ " " + dateOne.getMonth() + " " + dateOne.getYear()
						+ " , "
						+ (dateOne.getDaysOfDate() - dateTwo.getDaysOfDate()+1));

			}

		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
		}

	}

}
