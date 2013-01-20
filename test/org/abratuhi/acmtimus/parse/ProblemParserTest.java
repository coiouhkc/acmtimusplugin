package org.abratuhi.acmtimus.parse;

import junit.framework.TestCase;

import org.junit.Test;

public class ProblemParserTest extends TestCase{
	
	@Test
	public void testListProblems() {
		try {
			ProblemParser psp = new ProblemParser();
			psp.parse("1001", null);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
