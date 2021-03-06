package com.vub.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vub.model.Traject;
import com.vub.service.TrajectService;


@Controller
public class TrajectDashboardController {
@Autowired
TrajectService trajectService;

	/**
	 * @param model : model for /trajectdashbaord GET
	 * @return : returns trajectdashboardtable.jsp with trajectList as Set<Trajects>
	 */
	@RequestMapping(value = "/trajectdashboard", method = RequestMethod.GET)
	public String trajectDachbaord(ModelMap model) {
		Set<Traject> setTrajects = trajectService.getTrajects();
		model.addAttribute("trajectList", setTrajects);
		return "TrajectDashboardTable";
	}
	
	/**
	 * @param model : model for /trajectdashboard/new GET
	 * @return : returns TrajectAddDashboardTable
	 */
	@RequestMapping(value = "/trajectdashboard/new", method = RequestMethod.GET)
	public String trajectDachbaordNew(ModelMap model) {
		
		return "TrajectAddDashboard";
	}
	
	@RequestMapping(value = "/trajectdashboard/edit/{id}", method = RequestMethod.GET)
	public String trajectDachbaordEdit(@PathVariable int id, ModelMap model) {
		
		Traject traject = trajectService.findTrajectById(id);

		model.addAttribute("traject", traject);
		
		return "TrajectEditDashboard";
	}

}
