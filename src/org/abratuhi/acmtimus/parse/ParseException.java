package org.abratuhi.acmtimus.parse;

public class ParseException extends Exception{

	public ParseException() {}
	
	public ParseException(Throwable t) { super(t); }
	
	public ParseException(String s) { super(s); }
	
	public ParseException(String s, Throwable t) { super(s, t); }
}
