package com.dilan.aconex.util;

/**
 * Class Name : Constants
 * 
 * Description : Holds the constant values used throughout the program.
 * 
 * @author Dilan
 */
public class Constants {

	public static final String chars = "abcdefghijklmnopqrstuvwxyz";
	public static final String encoded = "22233344455566677778889999";
	public static final String phoneNumberReadError = "Error while reading the Phone Numbers file.";
	public static final String dictionaryReadError = "Error while reading the Dictionary file.";
	public static final String phoneNumberChunkSeperatorString = ":";
	public static final char phoneNumberChunkSeperatorChar = ':';
	public static final String matchSeperator = "[or]";
	public static final String phoneNumberCleanRegEx = "[^0-9]";
	public static final String defaultDictionaryLocation = "C:\\dev\\Aconex Coding Challenge - Select One\\Answer\\dic.txt";
	public static final String setDictionarySymbol = "-d";
	public static final String userInformationString = "You must sprcify a phone number list location as a command line argument. \n"
			+ "You have an option to specify a dictionary file by using the \"-d <dictionary_location>\" command. \n"
			+ "If a dictionary is not specified, a default dictionary will be set.";
	public static final String outputLine = "========================================================================";
	public static final String phoneNumberString = "Phone Number : ";
	public static final String resultsPrompt = "Please see below for results.";
	public static final String noMatchesFoundMEssage = "No matches found for this part of the number in the dictionary.";
	public static final String outputNumberSeperator = "-";

}
