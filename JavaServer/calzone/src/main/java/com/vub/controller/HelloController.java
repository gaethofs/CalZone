package com.vub.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vub.model.Course;
import com.vub.model.CourseComponent;
import com.vub.model.Entry;
import com.vub.model.ProfileSlot;
import com.vub.model.ProfileSlot.Badge;
import com.vub.model.ProfileSlot.Color;
import com.vub.model.Room;
import com.vub.model.Traject;
import com.vub.scheduler.Schedular;
import com.vub.scheduler.SchedularSolver;
import com.vub.scheduler.SchedulerInitializer;
import com.vub.service.EntryService;
import com.vub.service.RoomService;
import com.vub.service.TrajectService;

@Controller 
public class HelloController {

	@Autowired
	private TrajectService trajectService;

	@Autowired
	private RoomService roomService;

	@Autowired
	private EntryService entryService;

	@RequestMapping(value = "/hello")
	public String sayHello(Model model) {
		model.addAttribute("greeting", "Hello World");

		boolean allowed = false;
		
		
		if (allowed == true) {
			Set<Entry> entries = entryService.getEntries();
			for (Entry e: entries) {
				if (!e.isFrozen())  {
					entryService.deleteEntry(e);
				}
			}


			List<Room> roomsList = new ArrayList<Room>();
			roomsList.addAll(roomService.getRooms());

			Set<Traject> trajects = new HashSet<Traject>();
			Traject traject = new Traject();
			traject = trajectService.findTrajectByIdInitializedFull(177);
			//System.out.println(traject);


			trajects.add(traject);

			for (Traject t : trajects) {
				//System.out.println(t);
				for (Course c: t.getCourses()) {
					//System.out.println(c);
					for (CourseComponent cc: c.getCourseComponents()) {
						//System.out.println(cc); 
						for (User u: cc.getTeachers()) {
							System.out.println(u.getUsername());
						}
					}
				}
			}

			List<Date> dateSlots = SchedulerInitializer.createSlotsOfWeek(2014, 4);
			dateSlots.addAll(SchedulerInitializer.createSlotsOfWeek(2014, 5));
			dateSlots.addAll(SchedulerInitializer.createSlotsOfWeek(2014, 6));

		SchedulerSolver schedularSolver = new SchedulerSolver(dateSlots, roomsList, trajects);
		Scheduler schedular = schedularSolver.run();


			for (Entry e: schedular.getEntryList()) {
				entryService.updateEntry(e);
				System.out.println("Schedule: "  + e);
			}
		}
		
		List<ProfileSlot> profileSlots = new ArrayList<ProfileSlot>();
		ProfileSlot profileSlot = new ProfileSlot();
		profileSlot.setBadge(Badge.time);
		List<String> str = new ArrayList<String>();
		str.add("List");
		str.add("List 2");
		profileSlot.setTitle("Title");
		profileSlot.setDescriptions(str);
		profileSlots.add(profileSlot);
		profileSlot.setColor(null);
		ProfileSlot profileSlot2 = new ProfileSlot();
		profileSlot2.setBadge(null);
		profileSlot2.setColor(Color.success);
		profileSlot2.setIconText("10:00");
		profileSlot2.setDescriptions(str);
		profileSlot2.setTitle("Title 2");
		profileSlots.add(profileSlot2);
		profileSlots.add(profileSlot);
		profileSlots.add(profileSlot2);
		
		model.addAttribute("profileSlots", profileSlots);
		
		return "hello";
	}
}
