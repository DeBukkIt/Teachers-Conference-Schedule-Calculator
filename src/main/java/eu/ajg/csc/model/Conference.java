package eu.ajg.csc.model;

import java.util.ArrayList;

public class Conference {
	
	protected String name;
	protected ArrayList<Teacher> assignedTeachers;

	public Conference(String name, ArrayList<Teacher> assignedTeachers) {
		this.name = name;
		this.assignedTeachers = assignedTeachers;
	}

	public Conference(String name) {
		this(name, new ArrayList<>());
	}
	
	public void assignTeacher(Teacher t) {
		assignedTeachers.add(t);
		if(!t.isConferenceAssigned(this)) {
			t.assignToConference(this);
		}
	}
	
	public boolean isTeacherAssigned(Teacher t) {
		return assignedTeachers.contains(t);
	}
	
	public int getAssignedTeachersNum() {
		return assignedTeachers.size();
	}

	public ArrayList<Teacher> getAssignedTeachers() {
		return assignedTeachers;
	}
	
	public long getTeacherConferenceScore() {
		long sum = 0;
		for(Teacher t : assignedTeachers) {
			sum += t.getAssignedConferencesNum();
		}		
		return sum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "[\"" + name + "\", " + assignedTeachers.size() + " teachers]"; 
	}
	
}
