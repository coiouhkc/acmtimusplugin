package org.abratuhi.acmtimus.examples;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Sample file pre-created for every Problem exported for acm.timus.ru. 
 * 
 * @author Alexei Bratuhin
 *
 */
public class Main {
	public static final int PROBLEM_ID = 0;
	
	private static void solve(InputStream in, OutputStream out) {
		try {
			Scanner sc = new Scanner(in);
			PrintWriter pw = new PrintWriter(out);
			
			// INSERT YOUR SOLUTION CODE HERE

			pw.flush();
		} catch (Throwable t) {
			t.printStackTrace(new PrintStream(out));
		}
	}
	
	public static void main (String [] args) {
		solve(System.in, System.out);
	}

}
