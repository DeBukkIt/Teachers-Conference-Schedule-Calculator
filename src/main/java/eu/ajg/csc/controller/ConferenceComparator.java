package eu.ajg.csc.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import eu.ajg.csc.model.Conference;
import eu.ajg.csc.model.Schedule;
import eu.ajg.csc.model.Teacher;

public class ConferenceComparator {

	protected Schedule originalSchedule;
	protected Schedule optimizedSchedule;
	
	private int numTeachers = -1;
	
	protected final int MINUTES_PER_CONFERENCE = 15;
	
	public ConferenceComparator(Schedule originalSchedule, Schedule optimizedSchedule) {
		this.originalSchedule = new Schedule(scheduleSorter(originalSchedule, 10, 9, 8, 5, 6, 7));
		this.optimizedSchedule = optimizedSchedule;
	}
	
	private List<Conference> scheduleSorter(Schedule schedule, int... conferenceOrderKeys) {
		List<Conference> sortedConfList = new ArrayList<>();
		for(int orderKey : conferenceOrderKeys) {
			List<Conference> yearConferences = new ArrayList<>();
			for(Conference c : schedule.getConferences()) {
				if(c.getName().startsWith(String.valueOf(orderKey))) {
					yearConferences.add(c);
				}
			}
			yearConferences.sort((c1, c2) -> c1.getName().charAt(c1.getName().length() - 1) - c2.getName().charAt(c2.getName().length() - 1));
			sortedConfList.addAll(yearConferences);
		}
		return sortedConfList;
	}
	
	private int calculateWorkingHours(Schedule schedule) {		
		Set<Teacher> teachers = new HashSet<>();
		schedule.getConferences().stream().forEach(c -> teachers.addAll(c.getAssignedTeachers()));
		
		numTeachers = teachers.size();
		
		int lastConferenceIndexSum = 0;
		for(Teacher t : teachers) {
			_INNER:
			for(int i = schedule.getConferences().size() - 1; i >= 0; i--) {
				if(t.isConferenceAssigned(schedule.getConferences().get(i))) {
					lastConferenceIndexSum = i;
					break _INNER;
				}
			}
		}
		
		return teachers.size() * lastConferenceIndexSum * MINUTES_PER_CONFERENCE;
	}
	
	@Override
	public String toString() {
		float originalTime = (float) calculateWorkingHours(originalSchedule)/60.0f;
		float optimalTime = (float) calculateWorkingHours(optimizedSchedule)/60.0f;
		float timeDiff = originalTime - optimalTime;
		float timeDiffPerTeacher = timeDiff/numTeachers;
		
		 return "Not optimized: " + originalTime + " teacher-hours\n"
	          + "Optimized:     " + optimalTime  + " teacher-hours\n"
	          + "that's         " + timeDiffPerTeacher + " hours (" + Math.round(timeDiffPerTeacher*60) + " min) per teacher on average";
	}
	
}
