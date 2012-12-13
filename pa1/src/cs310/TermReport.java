package cs310;

import java.util.Scanner;

public class TermReport {
	public static final int NLINES = 500;
	private LineUsageData[] lines;

	public TermReport() {
		// Create an array of 500 SinglyLinkedLists
		// since there are NLINES = 500 individual lines,
		// numbered 1 to 500, so use array size NLINES+1
		lines = new LineUsageData[NLINES + 1];
		for (int i = 1; i <= NLINES; i++)
			lines[i] = new LineUsageData();
	}

	public static void main(String[] args) {
		TermReport reporter = new TermReport();
		reporter.collect();  // read input
		reporter.report();  // write report
	}

	// process input and fill lines array
	private void collect() {
		Scanner in = new Scanner(System.in);
		String line, user;
		int lineNumber;
		// For reading a text data file, with one record on each line,
		// it's best to read the lines separately and then analyze them:
		while (in.hasNextLine()) {
			line = in.nextLine();
			Scanner s = new Scanner(line);
			lineNumber = s.nextInt();
			user = s.next();
			// Place data in proper LineUsageData element of array.
			lines[lineNumber].addObservation(user);
		}
//
//                while (in.hasNextInt())
//                {
//                    lineNumber = in.nextInt();
//                    user = in.next();
//                    lines[lineNumber].addObservation(user);
//                }
	}

	// print out report on line usage
	private void report() {
		System.out.println("Line\tMost Common User\tCount");
		// Loop through array and print out the most common user
		// and number of logins.
		for (int i = 1; i <= NLINES; i++) {
			Usage record = lines[i].findMaxUsage();
			System.out.println(i + "\t" + record.getUser() + "\t"
					+ record.getCount());
		}
	}
}
