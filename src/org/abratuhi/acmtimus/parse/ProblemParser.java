package org.abratuhi.acmtimus.parse;

import java.net.URI;

import org.abratuhi.acmtimus.model.Problem;
import org.eclipse.core.net.proxy.IProxyData;
import org.eclipse.core.net.proxy.IProxyService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * This class represents the parser of a problem. Used for parsing the problem from its HTML representation on the acm.timus.ru page.
 * 
 * @author Alexei Bratuhin
 *
 */
public class ProblemParser {
	
	public static final String PROBLEM_URL = "http://acm.timus.ru/problem.aspx?space=1&num=";
	public static final String PROBLEM_SELECTOR = "table[align=CENTER]";
	public static final String PROBLEM_TITLE_SELECTOR = "table[align=CENTER]";
	public static final String PROBLEM_LIMIT_SELECTOR = "table[align=CENTER]";
	public static final String PROBLEM_TEXT_SELECTOR = "table[align=CENTER]";
	
	public final Problem parse(String num, IProxyService proxyService) throws ParseException{
		try {
			URI uri = new URI(PROBLEM_URL);
            IProxyData[] proxyDataForHost = proxyService.select(uri);
 
            for (IProxyData data : proxyDataForHost) {
                if (data.getHost() != null) {
                    System.setProperty("http.proxySet", "true");
                    System.setProperty("http.proxyHost", data.getHost());
                }
                if (data.getHost() != null) {
                    System.setProperty("http.proxyPort", String.valueOf(data
                            .getPort()));
                }
            }
            
			Document doc = Jsoup.connect(PROBLEM_URL + num).get();
			Element table = doc.select(PROBLEM_SELECTOR).first();
			
			Problem p = new Problem(num, table.toString());
			return p;
		} catch (Exception e) {
			throw new ParseException(e);
		}
	}

}
