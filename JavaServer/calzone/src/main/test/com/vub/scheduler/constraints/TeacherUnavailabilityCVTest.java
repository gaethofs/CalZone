/**
 * 
 */
package com.vub.scheduler.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.vub.model.CourseComponent;
import com.vub.model.CourseComponent.CourseComponentType;
import com.vub.model.Entry;
import com.vub.model.Room;
import com.vub.model.TeacherLecturePreference;
import com.vub.model.TeacherUnavailability;
import com.vub.model.Traject;
import com.vub.model.User;
import com.vub.scheduler.Helper;
import com.vub.scheduler.Scheduler;
import com.vub.scheduler.SchedulerScoreCalculator;
import com.vub.scheduler.SchedulerSolver;
import com.vub.utility.DateUtility;

/**
 * @author pieter
 * 
 */
public class TeacherUnavailabilityCVTest extends ConstraintViolationTest {
	public TeacherUnavailabilityCVTest() {
		super(RuleNames.teacherUnavailabilityViolated);
	}

	@Test
	public void testRuleFired() {
		// StartDateList
		List<Date> startDateList = new ArrayList<Date>();
		startDateList.add(DateUtility.createDate(2014, 7, 25, 10, 0)); // Day is
																		// monday

		// RoomList
		List<Room> roomList = Arrays.asList(Helper.createRoom());

		// TeacherUnavailability
		TeacherUnavailability teacherUnavailability = new TeacherUnavailability();
		teacherUnavailability.setDayOfWeek(2); // Not available on monday
		teacherUnavailability.setStartingHour(10);
		teacherUnavailability.setEndingHour(12);

		HashSet<TeacherUnavailability> teacherUnavailabilities = new HashSet<TeacherUnavailability>();
		teacherUnavailabilities.add(teacherUnavailability);

		// Course list
		HashSet<User> teachers = Helper.createTeachers("Tim");
		teachers.iterator().next()
				.setTeacherUnavailabilities(teacherUnavailabilities);
		List<CourseComponent> ccList = new ArrayList<CourseComponent>();

		CourseComponent cc = Helper.createCourseComponent(teachers, 20, 4, 2,
				CourseComponentType.HOC);
		cc.setEndingDate(DateUtility.createDate(2014, 6, 1));
		ccList.add(cc);

		// Traject list
		Set<Traject> trajectSet = Helper.createTraject(ccList);

		// Entry list
		List<Entry> entryList = SchedulerSolver.createEntryList(startDateList,
				roomList, trajectSet);
		entryList.get(0).setStartingDate(startDateList.get(0));
		logEntries(this.ruleName, entryList);

		// Initialize solution
		Scheduler solution = new Scheduler(startDateList, roomList, entryList,
				trajectSet);
		SchedulerScoreCalculator ssc = new SchedulerScoreCalculator(solution);

		Collection<String> constraintNames = getViolatedConstraintNames(ssc
				.getScoreDirector());

		assertTrue("No " + this.ruleName + " detected.",
				constraintNames.contains(this.ruleName));
		assertConstraintViolationObject(ssc);
	}

	/**
	 * Test that the rule doensn'it fire if it doesn't have to.
	 */
	@Test
	public void testRuleNotFired() {
		// StartDateList
		List<Date> startDateList = new ArrayList<Date>();
		// Date is monday
		startDateList.add(DateUtility.createDate(2014, 7, 25, 10, 0));

		// RoomList
		List<Room> roomList = Arrays.asList(Helper.createRoom());

		// TeacherUnavailability
		TeacherUnavailability teacherUnavailability = new TeacherUnavailability();
		teacherUnavailability.setDayOfWeek(3); // Not available on tuesday
		teacherUnavailability.setStartingHour(10);
		teacherUnavailability.setEndingHour(12);

		HashSet<TeacherUnavailability> teacherUnavailabilities = new HashSet<TeacherUnavailability>();
		teacherUnavailabilities.add(teacherUnavailability);

		// Course list
		HashSet<User> teachers = Helper.createTeachers("Tim");
		teachers.iterator().next()
				.setTeacherUnavailabilities(teacherUnavailabilities);
		List<CourseComponent> ccList = new ArrayList<CourseComponent>();

		CourseComponent cc = Helper.createCourseComponent(teachers, 20, 4, 2,
				CourseComponentType.HOC);
		cc.setEndingDate(DateUtility.createDate(2014, 6, 1));
		ccList.add(cc);

		// Traject list
		Set<Traject> trajectSet = Helper.createTraject(ccList);

		// Entry list
		List<Entry> entryList = SchedulerSolver.createEntryList(startDateList,
				roomList, trajectSet);
		entryList.get(0).setStartingDate(startDateList.get(0));
		logEntries(this.ruleName, entryList);

		// Initialize solution
		Scheduler solution = new Scheduler(startDateList, roomList, entryList,
				trajectSet);
		SchedulerScoreCalculator ssc = new SchedulerScoreCalculator(solution);

		Collection<String> constraintNames = getViolatedConstraintNames(ssc
				.getScoreDirector());

		assertFalse("A " + this.ruleName + " is detected.",
				constraintNames.contains(this.ruleName));
		assertConstraintViolationObject(ssc);

	}
}
