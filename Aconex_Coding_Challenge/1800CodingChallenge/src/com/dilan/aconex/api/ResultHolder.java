package com.dilan.aconex.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name : ResultHolder
 * 
 * Description : POJO to hold the results found during processing. This also
 * acts as a DTO object to carry the processed data until its printed out.
 * 
 * @author Dilan
 */
public class ResultHolder {

	private String phoneNumber;
	private int numberOfParts;
	private List<MatchResults> suggestions;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getNumberOfParts() {
		return numberOfParts;
	}

	public void setNumberOfParts(int numberOfParts) {
		this.numberOfParts = numberOfParts;
	}

	public List<MatchResults> getSuggestions() {
		return suggestions;
	}

	/**
	 * Adds the given object to the suggestions array list. If the list is
	 * not there, a new list will be created and the object will be added to
	 * that.
	 */
	public void addSuggestions(MatchResults matchResults) {

		if (null == this.suggestions) {
			this.suggestions = new ArrayList<MatchResults>();
			this.suggestions.add(matchResults);
		} else {
			this.suggestions.add(matchResults);
		}
	}

	public void setSuggestions(List<MatchResults> suggestions) {

		this.suggestions = suggestions;

	}

}
