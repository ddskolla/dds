package com.dilan.rea.api;

/**
 * This calls is a custom date class defined to hold the year,month and date values.
 * It also calculates the number of days for the given date. 
 * @author Dilan
 */
public class CustomDate {

	private int year;
	private int month;
	private int date;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getDaysOfDate() {

		int numberOfDaysInDate = 0;
		
		numberOfDaysInDate += (this.year-1) * 365;
		numberOfDaysInDate += getPastDaysForTheYear(this.month-1);
		numberOfDaysInDate += this.date;

		return numberOfDaysInDate;
	}

	private int getPastDaysForTheYear(int month) {

		int daysPassed = 0;

		switch (month) {
		case 1:
			daysPassed = 31;
			break;
		case 2:
			daysPassed = 59;
			break;
		case 3:
			daysPassed = 90;
			break;
		case 4:
			daysPassed = 120;
			break;
		case 5:
			daysPassed = 151;
			break;
		case 6:
			daysPassed = 181;
			break;
		case 7:
			daysPassed = 212;
			break;
		case 8:
			daysPassed = 243;
			break;
		case 9:
			daysPassed = 273;
			break;
		case 10:
			daysPassed = 304;
			break;
		case 11:
			daysPassed = 334;
			break;
		}

		return daysPassed;

	}

}
