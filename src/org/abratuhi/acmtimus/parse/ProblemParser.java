package org.abratuhi.acmtimus.parse;

import org.abratuhi.acmtimus.model.Problem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ProblemParser {
	
	public static final String PROBLEM_URL = "http://acm.timus.ru/problem.aspx?space=1&num=";
	public static final String PROBLEM_SELECTOR = "table[align=CENTER]";
	public static final String PROBLEM_TITLE_SELECTOR = "table[align=CENTER]";
	public static final String PROBLEM_LIMIT_SELECTOR = "table[align=CENTER]";
	public static final String PROBLEM_TEXT_SELECTOR = "table[align=CENTER]";
	
	public final Problem parse(String num) throws ParseException{
		try {
			Document doc = Jsoup.connect(PROBLEM_URL + num).get();
			Element table = doc.select(PROBLEM_SELECTOR).first();
			
			Problem p = new Problem(num, table.toString());
			return p;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

}
