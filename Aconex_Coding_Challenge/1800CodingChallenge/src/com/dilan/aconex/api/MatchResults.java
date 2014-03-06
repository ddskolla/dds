package com.dilan.aconex.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Name : MatchResults
 * 
 * Description : POJO to hold the information on the matches found during
 * processing. This also acts as a DTO object to carry the processed data until
 * its printed out.
 * 
 * @author Dilan
 */
public class MatchResults {

	String part;
	boolean ismatchFound;
	List<String> matches;

	public String getPart() {
		return part;
	}

	public void setPart(String part) {
		this.part = part;
	}

	public boolean isIsmatchFound() {
		return ismatchFound;
	}

	public void setIsmatchFound(boolean ismatchFound) {
		this.ismatchFound = ismatchFound;
	}

	public List<String> getMatches() {
		return matches;
	}

	public void setMatches(List<String> matches) {

		this.matches = matches;

	}

	/**
	 * Adds the given string to the matches string array list. If the list is
	 * not there, a new list will be created and the value will be added to
	 * that.
	 */
	public void addMatches(String matches) {

		if (null == this.matches) {
			this.matches = new ArrayList<String>();
			this.matches.add(matches);
		} else {
			this.matches.add(matches);
		}
	}

}
