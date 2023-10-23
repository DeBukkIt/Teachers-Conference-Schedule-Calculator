package eu.ajg.csc.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

import eu.ajg.csc.io.InputReader;
import eu.ajg.csc.model.Conference;
import eu.ajg.csc.model.Schedule;

public class StartConferenceScheduleCalculator {

	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		System.out.println("=== Conference Order Optimizer ===");
		System.out.println("Version: 1.0.0 (23.10.2023)       \n");
		
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
		for(int i = 0; i < schedule.getConferences().size(); i++) {
			Conference conf = schedule.getConferences().get(i);
			int numTeachers = conf.getAssignedTeachersNum();
			double avgConfsPerTeacher = (double) conf.getTeacherConferenceScore() / (double) conf.getAssignedTeachersNum();
			System.out.println((i+1) + ". " + conf.getName() + " (" + numTeachers + " teachers, " + avgConfsPerTeacher + " conferences per teacher (avg.))");
		}
		
		// Print time statistics
		double timeDiff = (System.nanoTime() - timeStart) / 1E6;
		System.out.println("\n\nFound best 1 of " + factorial(schedule.getConferences().size()) + " possible solutions in " + new DecimalFormat("##.##").format(timeDiff) + " ms");
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
