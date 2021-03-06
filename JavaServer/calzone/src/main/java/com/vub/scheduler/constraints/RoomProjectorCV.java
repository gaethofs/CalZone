package com.vub.scheduler.constraints;

import com.vub.model.Entry;
import com.vub.utility.DateUtility;

public class RoomProjectorCV implements ConstraintViolation {
	Entry entry;

	public RoomProjectorCV(Entry entry) {
		this.entry = entry;
	}

	@Override
	public String description() {
		// TODO Internationalize
		String msg = "Course ";
		msg += entry.getCourseComponent().getCourse().getCourseName();
		msg += " given at ";
		msg += DateUtility.formatAsDateTime(entry.getStartingDate());
		msg += " requires a projector which is not available in ";
		msg += entry.getRoom().getDisplayName();
		msg += ".";
		
		return msg;
	}

}
