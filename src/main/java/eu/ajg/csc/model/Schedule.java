package eu.ajg.csc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A class that manages a list of Conference objects. In particular, it has a
 * method for optimising the order of these conference objects. This makes it
 * the functional core component of the programme.
 */
public class Schedule {

	/**
	 * A list of Conference objects
	 */
	protected ArrayList<Conference> conferences;

	/**
	 * Creates an object of the Schedule class based on a collection of Conference
	 * objects. These are stored in a new ArrayList.
	 * 
	 * @param conferences A collection of Conference objects
	 */
	public Schedule(Collection<Conference> conferences) {
		this.conferences = new ArrayList<>();
		this.conferences.addAll(conferences);
	}

	/**
	 * Optimises the order of conferences by sorting them stably according to 1. the
	 * "Teacher Conference Score", i.e. the sum of conferences attended by teachers
	 * assigned to that conference, and 2. the number of teachers assigned to that
	 * conference. The conference objects stored in the ArrayList are then found in
	 * this optimised order. The optimised list can be retrieved via the
	 * corresponding getter.
	 */
	public void optimizeOrder() {
		conferences = (ArrayList<Conference>) conferences.stream()
				.sorted((c1, c2) -> (int) (c1.getTeacherConferenceScore() - c2.getTeacherConferenceScore()))
				.sorted((c1, c2) -> c2.getAssignedTeachersNum() - c1.getAssignedTeachersNum())
				.collect(Collectors.toList());
	}

	/**
	 * Returns the stored ArrayList of Conference objects. If their order is to be
	 * optimised, <code>optimizeOrder()</code> must be called beforehand.
	 * 
	 * @return The stored ArrayList of Conference objects
	 */
	public ArrayList<Conference> getConferences() {
		return conferences;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < conferences.size(); i++) {
			sb.append((i + 1) + ". " + conferences.get(i) + "\n");
		}
		return sb.toString().trim();
	}

}
