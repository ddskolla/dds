package com.dilan.rea.util;

import java.util.ArrayList;

import com.dilan.rea.api.CustomDate;
import com.dilan.rea.exception.InvalidInputException;

/**
 * This class validates the input taken from the file and throws a custom
 * exception if data is not in the accepted format.
 * 
 * @author Dilan
 * 
 */
public class DateValidator {

	public String[] validateInputString(String inputString)
			throws InvalidInputException {

		String[] dateArray;
		inputString = inputString.trim();

		if (inputString.contains(",")) {

			dateArray = inputString.split(",");

		} else {
			throw new InvalidInputException(
					"Input data format is invalid, Please check and try again");
		}

		return dateArray;

	}

	public ArrayList<CustomDate> validateAndPopulateDates(String[] datesArray)
			throws InvalidInputException {

		ArrayList<CustomDate> datesList = new ArrayList<CustomDate>();

		String dateOne = datesArray[0];
		dateOne = dateOne.trim();
		String[] dateOneArray = dateOne.split(" ");

		int dateOneDate = Integer.parseInt(dateOneArray[0]);
		validateDate(dateOneDate);

		int dateOneMonth = Integer.parseInt(dateOneArray[1]);
		validateMonth(dateOneMonth);

		int dateOneYear = Integer.parseInt(dateOneArray[2]);
		validateYear(dateOneYear);

		validateFebruary(dateOneMonth, dateOneDate);

		CustomDate customDateOne = new CustomDate();
		customDateOne.setDate(dateOneDate);
		customDateOne.setMonth(dateOneMonth);
		customDateOne.setYear(dateOneYear);

		String dateTwo = datesArray[1];
		dateTwo = dateTwo.trim();
		String[] dateTwoArray = dateTwo.split(" ");

		int dateTwoDate = Integer.parseInt(dateTwoArray[0]);
		validateDate(dateTwoDate);

		int dateTwoMonth = Integer.parseInt(dateTwoArray[1]);
		validateMonth(dateTwoMonth);

		int dateTwoYear = Integer.parseInt(dateTwoArray[2]);
		validateYear(dateTwoYear);

		validateFebruary(dateTwoMonth, dateTwoDate);

		CustomDate customDateTwo = new CustomDate();
		customDateTwo.setDate(dateTwoDate);
		customDateTwo.setMonth(dateTwoMonth);
		customDateTwo.setYear(dateTwoYear);

		if (customDateOne.getDaysOfDate() > customDateTwo.getDaysOfDate()) {

			datesList.add(customDateOne);
			datesList.add(customDateTwo);

		} else {

			datesList.add(customDateTwo);
			datesList.add(customDateOne);

		}

		return datesList;

	}

	private void validateFebruary(int month, int date)
			throws InvalidInputException {

		if (month == 2 && date > 28) {

			throw new InvalidInputException(
					"February only has 28 days, Please check and try again");

		}

	}

	private void validateYear(int year) throws InvalidInputException {

		if (1900 > year || 2010 < year) {
			throw new InvalidInputException(
					"A provided year falls out of valid range, Please check and try again");
		}

	}

	private void validateMonth(int month) throws InvalidInputException {

		if (1 > month || 12 < month) {
			throw new InvalidInputException(
					"A provided month falls out of valid range, Please check and try again");
		}

	}

	private void validateDate(int date) throws InvalidInputException {

		if (1 > date || 31 < date) {
			throw new InvalidInputException(
					"A provided date falls out of valid range, Please check and try again");
		}

	}
}
