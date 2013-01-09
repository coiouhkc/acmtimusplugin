package org.abratuhi.acmtimus.parse;

import java.util.List;
import java.util.Vector;

import org.abratuhi.acmtimus.model.ProblemRef;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProblemSetParser {
	
	public static final String PROBLEM_SET_URL = "http://acm.timus.ru/problemset.aspx?space=1&page=all";
	public static final String PROBLEM_SET_TABLE_SELECTOR = "table[class~=problemset\\sstrict]";
	public static final String PROBLEM_SET_TR_SELECTOR = "tr";
	public static final String PROBLEM_SET_TH_SELECTOR = "th";
	public static final String PROBLEM_SET_TD_SELECTOR = "td";
	
	public final List<ProblemRef> parse(String url) throws ParseException {
		List<ProblemRef> result = new Vector<ProblemRef>();
		try {
			Document doc = Jsoup.connect(url).get();
			Element table = doc.select(PROBLEM_SET_TABLE_SELECTOR).first();
			Elements rows = table.select(PROBLEM_SET_TR_SELECTOR);
			for(Element row : rows) {
				if (row.select(PROBLEM_SET_TH_SELECTOR).isEmpty()) {
					Elements cells = row.select(PROBLEM_SET_TD_SELECTOR);
					final String id = cells.get(1).text();
					final String title = cells.get(2).text();
					ProblemRef pf = new ProblemRef(id, title);
					result.add(pf);
				}
			}
		} catch (Exception e) {
			throw new ParseException(e);
		}
		return result;
	}

}
