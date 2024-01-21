package eu.ajg.csc.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import eu.ajg.csc.io.InputReader;
import eu.ajg.csc.model.Schedule;

public class StartConferenceScheduleCalculator {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		System.out.println("=== Conference Order Optimizer ===");
		System.out.println("Version: 1.2.0 (21.01.2024)       \n");
		
		// Check input
		if(!checkInput(args)) {
			System.exit(0);
		}
		
		// Start time measurement
		long timeStart = System.nanoTime();
		
		// Read input file and calculate optimal order
		InputReader inputReader = new InputReader(args[0]);
		Schedule schedule = inputReader.readInput();
		schedule.optimizeOrder();
		
		// Print output
		System.out.println("== Optimal conference order ==");
		for(int i = 0; i < schedule.getConferences().size(); i++) {
			System.out.println((i+1) + ". " + schedule.getConferences().get(i));
		}
		
		// Calculate comparison (1/2)
		ConferenceComparator confComp = new ConferenceComparator(inputReader.readInput(), schedule);
		System.out.println("\n" + confComp.toString());
		
		// Print time statistics
		double timeDiff = (System.nanoTime() - timeStart) / 1E6;
		long fac = factorial(schedule.getConferences().size());
		if(fac < 0) {
			System.out.println("\nFound best 1 of " + schedule.getConferences().size() + "! possible solutions in " + new DecimalFormat("##.##").format(timeDiff) + " ms");
		} else {
			System.out.println("\nFound best 1 of " + fac + " possible solutions in " + new DecimalFormat("##.##").format(timeDiff) + " ms");
		}
		
		// Find parallelizable conferences
		System.out.println("\nChecking for possible parallelization of conferences...");
		if(schedule.parallelizeConferences()) {
			schedule.optimizeOrder();
			
			// Print output
			System.out.println("\n== Optimal conference order with parallel conferences ==");
			for(int i = 0; i < schedule.getConferences().size(); i++) {
				System.out.println((i+1) + ". " + schedule.getConferences().get(i));
			}
		} else {
			System.out.println("Parallelization not possible.");
		}
		
		// Calculate comparison (2/2)
		confComp = new ConferenceComparator(inputReader.readInput(), schedule);
		System.out.println("\n" + confComp.toString());
	}
	
	private static boolean checkInput(String[] args) {
		if(args == null || args.length < 1 || args[0].isEmpty()) {
			System.err.println("Missing parameter. Correct syntax is:");
			System.err.println("program <pathToInputCSVFile>");
			System.err.println("        --> The path to a CSV file with one column per conference and\n"
							 + "            one row per teacher (plus one row for headings). Each\n"
							 + "            non-empty cell is counted as an assignment between teacher\n"
							 + "            and conference.");
			return false;
		}
		
		return true;
	}
	
	private static long factorial(int n) {
		if (n == 1) {
			return n;
		}
		return factorial(n-1) * n;
	}

}
