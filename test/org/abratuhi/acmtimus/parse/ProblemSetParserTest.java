package org.abratuhi.acmtimus.parse;

import java.util.List;

import junit.framework.TestCase;

import org.abratuhi.acmtimus.model.ProblemRef;
import org.junit.Test;

public class ProblemSetParserTest extends TestCase{
	
	@Test
	public void testListProblems() {
		try {
			ProblemSetParser psp = new ProblemSetParser();
			List<ProblemRef> pfs = psp.parse(ProblemSetParser.PROBLEM_SET_URL, null);
			for(ProblemRef pf: pfs) {
				System.out.println(pf.getId() + ": " + pf.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
