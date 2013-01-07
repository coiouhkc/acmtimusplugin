package org.abratuhi.acmtimus.model;

public class ProblemRef {
	
	private final String id;
	private final String name;
	
	public ProblemRef(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		return new String(id + ": " + name);
	}
	

}
