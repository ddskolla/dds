package com.dilan.aconex.util;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dilan.aconex.TestConstants;
import com.dilan.aconex.exception.FileReadingException;

/**
 * Class Name : NumberListProcessorTest
 * 
 * Description : Unit test for the NumberListProcessor class.
 * 
 * @author Dilan
 */
public class NumberListProcessorTest {

	private NumberListProcessor numberListProcessor;

	@Before
	public void setup() {
		numberListProcessor = new NumberListProcessor();
	}

	@Test(expected = FileReadingException.class)
	public void testProcessPhoneNumbersException() throws FileReadingException {

		numberListProcessor.processPhoneNumbers(TestConstants.incorrectLocation);

	}

	@Test
	public void testProcessPhoneNumbers() throws FileReadingException {
		
		List<String> numberList = numberListProcessor.processPhoneNumbers(TestConstants.testNumberListLocation);
		assertEquals("testProcessPhoneNumbers method failed", numberList.size(),5);
		
	}
}
