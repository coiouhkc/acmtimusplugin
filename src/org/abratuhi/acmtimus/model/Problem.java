package org.abratuhi.acmtimus.model;

/**
 * This class represents the complete problem.
 * 
 * @author Alexei Bratuhin
 *
 */
public class Problem {
	
	/**
	 * Problem id, ProblemNum.
	 */
	private final String id;
	
	/**
	 * Problem raw inner HTML text.
	 */
	private final String raw;
	
	private String title;
	private String restrictions;
	private String description;
	private String input;
	private String output;
	private String sample;
	
	public Problem(String id, String raw) {
		this.id = id;
		this.raw = raw;
	}
	
	public String getRaw() {
		return raw;
	}
	
	public String getId() {
		return id;
	}

}
