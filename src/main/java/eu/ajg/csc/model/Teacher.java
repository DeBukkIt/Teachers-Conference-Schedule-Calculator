package eu.ajg.csc.model;

import java.util.ArrayList;

public class Teacher {
	
	protected String name;
	protected ArrayList<Conference> assignedConferences;
	
	public Teacher (String name) {
		this.name = name;
		this.assignedConferences = new ArrayList<>();
	}
	
	public void assignToConference(Conference c) {
		assignedConferences.add(c);
		if(!c.isTeacherAssigned(this)) {
			c.assignTeacher(this);
		}
	}
	
	public boolean isConferenceAssigned(Conference c) {
		return assignedConferences.contains(c);
	}
	
	public int getAssignedConferencesNum() {
		return assignedConferences.size();
	}
	
	public ArrayList<Conference> getAssignedConferences() {
		return assignedConferences;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "[" + name + "]";
	}

}
