package com.dilan.aconex.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.dilan.aconex.TestConstants;
import com.dilan.aconex.api.MatchResults;
import com.dilan.aconex.api.ResultHolder;

/**
 * Class Name : DataProcessorTest
 * 
 * Description : Unit test for the DataProcessor class.
 * 
 * @author Dilan
 */
public class DataProcessorTest {

	private DataProcessor dataProcessor;
	private Map<String, List<String>> dictionaryMap;
	List<String> phoneNumberList;

	@Before
	public void setup() {

		dataProcessor = new DataProcessor();

		dictionaryMap = new HashMap<String, List<String>>();

		List<String> stringListOne = new ArrayList<String>();
		stringListOne.add("flower");
		dictionaryMap.put("356937", stringListOne);

		List<String> stringListTwo = new ArrayList<String>();
		stringListTwo.add("me");
		stringListTwo.add("ne");
		dictionaryMap.put("63", stringListTwo);

		List<String> stringListThree = new ArrayList<String>();
		stringListThree.add("call");
		dictionaryMap.put("2255", stringListThree);

		phoneNumberList = new ArrayList<String>();

		phoneNumberList.add("356937");
		phoneNumberList.add("63");
		phoneNumberList.add("2255:63");

	}

	@Test
	public void testProcessDataCheckListSize() {

		List<ResultHolder> outputData = dataProcessor.processData(
				dictionaryMap, phoneNumberList);
		assertEquals("testProcessData " + TestConstants.methodFailedMessageString, outputData.size(), 3);

	}

	@Test
	public void testProcessDataCheckDataConsistancy() {

		List<ResultHolder> outputData = dataProcessor.processData(
				dictionaryMap, phoneNumberList);

		for (ResultHolder result : outputData) {

			assertEquals("testProcessDataValues " + TestConstants.methodFailedMessageString,
					result.getNumberOfParts(), result.getSuggestions().size());

		}

	}

	@Test
	public void testProcessDataCheckValues() {

		List<ResultHolder> outputData = dataProcessor.processData(
				dictionaryMap, phoneNumberList);

		for (ResultHolder result : outputData) {

			for (MatchResults match : result.getSuggestions()) {

				assertTrue("testProcessDataCheckValues " + TestConstants.methodFailedMessageString,result.getPhoneNumber().contains(match.getPart()));

			}

		}

	}

}
