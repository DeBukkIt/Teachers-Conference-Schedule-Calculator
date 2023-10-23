package eu.ajg.csc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

import eu.ajg.csc.model.Conference;
import eu.ajg.csc.model.Schedule;
import eu.ajg.csc.model.Teacher;

public class InputReader {
	
	protected File inputFile;
	
	public InputReader(String filePath) {
		inputFile = new File(filePath);
		
		if(inputFile.isDirectory() || !inputFile.exists() || !inputFile.canRead()) {
			throw new IllegalArgumentException("File is directory or does not exist or cannot be read");
		}
	}
	
	public Schedule readInput() throws UnsupportedEncodingException, FileNotFoundException {
		ArrayList<Conference> result = new ArrayList<>();
		
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8")));

		// Read conference names from first line (headings of csv file)
		String firstLine = s.nextLine();
		String[] firstLineParts = firstLine.split(";");
		for(int i = 1; i < firstLineParts.length; i++) {
			result.add(new Conference(firstLineParts[i]));
		}
		
		while(s.hasNextLine()) {
			// Read line
			String line = s.nextLine();
			// Split line
			String[] lineParts = line.split(";");
			// Add a new teacher
			Teacher newTeacher = new Teacher(lineParts[0]);
			// For every conference in the input csv file...
			for(int i = 1; lineParts != null && i < lineParts.length; i++) {
				// assign the new teacher to that conference
				if(!lineParts[i].isEmpty()) {
					result.get(i - 1).assignTeacher(newTeacher);
				}
				
			}
		}
		
		return new Schedule(result.stream().collect(Collectors.toSet()));
	}

}
