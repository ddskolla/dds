package com.dilan.aconex.api;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.dilan.aconex.TestConstants;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * Class Name : PojoTest
 * 
 * Description : This class uses the OpenPojo.jar to test the POJO objects in the com.dilan.aconex.api package. 
 * 
 * @author Dilan
 */
public class PojoTest {

	private List<PojoClass> pojoClasses;
	private PojoValidator pojoValidator;

	private MatchResults matchResultsTestObject;
	private ResultHolder resultHolderTestObject;

	@Before
	public void setup() {
		pojoClasses = PojoClassFactory.getPojoClasses(TestConstants.pojoPackage,
				new FilterPackageInfo());

		pojoValidator = new PojoValidator();

		pojoValidator.addRule(new GetterMustExistRule());
		pojoValidator.addRule(new SetterMustExistRule());

		pojoValidator.addTester(new SetterTester());
		pojoValidator.addTester(new GetterTester());

		matchResultsTestObject = new MatchResults();
		resultHolderTestObject = new ResultHolder();

	}

	@Test
	public void testPojoStructureAndBehavior() {
		for (PojoClass pojoClass : pojoClasses) {
			pojoValidator.runValidation(pojoClass);
		}
	}
	
	@Test
	public void testAddSuggestionsWithNull() {

		MatchResults matchResults = new MatchResults();
		matchResults.setPart(TestConstants.testStringOne);
		resultHolderTestObject.addSuggestions(matchResults);

		assertEquals("testAddSuggestionsWithNull " + TestConstants.methodFailedMessageString,
				resultHolderTestObject.getSuggestions().get(0).getPart(), TestConstants.testStringOne);
	}
	
	@Test
	public void testAddSuggestionsWithValue() {

		MatchResults matchResultsOne = new MatchResults();
		matchResultsOne.setPart(TestConstants.testStringOne);
		MatchResults matchResultsTwo = new MatchResults();
		matchResultsTwo.setPart(TestConstants.testStringTwo);
		
		resultHolderTestObject.addSuggestions(matchResultsOne);
		resultHolderTestObject.addSuggestions(matchResultsTwo);

		assertEquals("testAddSuggestionsWithValue " + TestConstants.methodFailedMessageString,
				resultHolderTestObject.getSuggestions().get(1).getPart(), TestConstants.testStringTwo);
	}

	@Test
	public void testAddMethodForMatchesWithNull() {

		matchResultsTestObject.addMatches(TestConstants.testStringOne);

		assertEquals("testAddMethodForMatchesWithNull " + TestConstants.methodFailedMessageString,
				matchResultsTestObject.getMatches().get(0), TestConstants.testStringOne);
	}

	@Test
	public void testAddMethodForMatchesWithValue() {

		matchResultsTestObject.addMatches(TestConstants.testStringOne);
		matchResultsTestObject.addMatches(TestConstants.testStringTwo);

		assertEquals("testAddMethodForMatchesWithValue " + TestConstants.methodFailedMessageString,
				matchResultsTestObject.getMatches().get(1), TestConstants.testStringTwo);
	}

	public List<PojoClass> getPojoClasses() {
		return pojoClasses;
	}

	public void setPojoClasses(List<PojoClass> pojoClasses) {
		this.pojoClasses = pojoClasses;
	}

	public PojoValidator getPojoValidator() {
		return pojoValidator;
	}

	public void setPojoValidator(PojoValidator pojoValidator) {
		this.pojoValidator = pojoValidator;
	}

	public MatchResults getMatchResultsTestObject() {
		return matchResultsTestObject;
	}

	public void setMatchResultsTestObject(MatchResults matchResultsTestObject) {
		this.matchResultsTestObject = matchResultsTestObject;
	}

	public ResultHolder getResultHolderTestObject() {
		return resultHolderTestObject;
	}

	public void setResultHolderTestObject(ResultHolder resultHolderTestObject) {
		this.resultHolderTestObject = resultHolderTestObject;
	}
}
