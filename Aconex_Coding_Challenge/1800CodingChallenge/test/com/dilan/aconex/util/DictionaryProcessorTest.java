package com.dilan.aconex.util;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.dilan.aconex.TestConstants;
import com.dilan.aconex.exception.FileReadingException;

/**
 * Class Name : DictionaryProcessorTest
 * 
 * Description : Unit test for the DictionaryProcessor class.
 * 
 * @author Dilan
 */
public class DictionaryProcessorTest {

	private DictionaryProcessor dictionaryProcessor;

	@Before
	public void setup() {
		dictionaryProcessor = new DictionaryProcessor();
	}

	@Test(expected = FileReadingException.class)
	public void testProcessDictionaryException() throws FileReadingException {

		dictionaryProcessor.processDictionary(TestConstants.incorrectLocation);
	}

	@Test
	public void testProcessDictionary() throws FileReadingException {
		
		Map<String, List<String>> dictionaryMap = dictionaryProcessor.processDictionary(TestConstants.testDictionaryLocation);
		assertEquals("testProcessDictionary " + TestConstants.methodFailedMessageString , dictionaryMap.size(),5);
		
	}
}
