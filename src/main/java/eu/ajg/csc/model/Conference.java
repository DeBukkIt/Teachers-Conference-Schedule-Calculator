package eu.ajg.csc.model;

import java.util.ArrayList;

public class Conference {
	
	protected String name;
	protected ArrayList<Teacher> assignedTeachers;
	protected Conference parallelConference;

	public Conference(String name, ArrayList<Teacher> assignedTeachers) {
		this.name = name;
		this.assignedTeachers = assignedTeachers;
		this.parallelConference = null;
	}

	public Conference(String name) {
		this(name, new ArrayList<>());
	}
	
	public Conference() {
		this(null, new ArrayList<>());
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
	
	public int getAssignedTeachersNumParallel() {
		return getAssignedTeachersNum() + (hasParallelConference() ? getParallelConference().getAssignedTeachersNum() : 0);
	}

	public ArrayList<Teacher> getAssignedTeachers() {
		return assignedTeachers;
	}
	
	public int getTeacherConferenceScore() {
		int sum = 0;
		for(Teacher t : assignedTeachers) {
			sum += t.getAssignedConferencesNum();
		}		
		return sum;
	}
	
	public int getTeacherConferenceScoreParallel() {
		return getTeacherConferenceScore() + (hasParallelConference() ? getParallelConference().getTeacherConferenceScore() : 0);
	}
	
	public boolean doTeachersIntersectWith(Conference otherConf) {
		for(Teacher someConfTeacher : assignedTeachers) {
			for(Teacher otherConfTeacher : otherConf.getAssignedTeachers()) {
				if(someConfTeacher.equals(otherConfTeacher)) {
					return true;
				}
			}
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Conference getParallelConference() {
		return parallelConference;
	}

	public void setParallelConference(Conference parallelConference) {
		this.parallelConference = parallelConference;
		
		if(parallelConference.getParallelConference() != this) {
			parallelConference.setParallelConference(this);
		}
	}
	
	public boolean hasParallelConference() {
		return parallelConference != null;
	}
	
	public boolean isParallelWith(Conference other) {
		return parallelConference == other;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Conference)) {
			return false;
		}
		
		Conference other = (Conference) obj;
		if(other != null && other.getName() != null && other.getAssignedTeachers() != null) {
			return other.getName().equals(name) && other.getAssignedTeachers().equals(assignedTeachers);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		if(hasParallelConference()) {
			return "[\"" + name + "\", " + assignedTeachers.size() + " teachers || \"" + parallelConference.getName() + "\", " + parallelConference.assignedTeachers.size() + " teachers]";
		} else {
			return "[\"" + name + "\", " + assignedTeachers.size() + " teachers]";
		}
	}
	
}
