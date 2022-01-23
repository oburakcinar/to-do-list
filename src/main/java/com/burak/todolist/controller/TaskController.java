package com.burak.todolist.controller;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.burak.todolist.entity.Task;
import com.burak.todolist.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	
	//removing leading and trailing spaces
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true); 
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	
	@GetMapping("/list")
	public String showList(Model theModel) {
		List<Task> taskList = taskService.findAllByOrderByDueDateAsc();
		
		for(Task tempTask : taskList) {
			String dayRemaining = findDayDifference(tempTask);
			tempTask.setDaysRemaining(dayRemaining);
			taskService.save(tempTask);
		}
		
		theModel.addAttribute("taskList", taskList);
		return "task-list";  
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		Task theTask = new Task();
		theModel.addAttribute("task", theTask);
		return "task-form";
	}
	
	
	@PostMapping("/save")
	public String saveTask(@Valid @ModelAttribute("task") Task theTask, BindingResult theBindingResult) {
		if (theBindingResult.hasErrors()) {
			return "task-form";
		} else {
			String daysRemaining = findDayDifference(theTask);
			theTask.setDaysRemaining(daysRemaining);
			taskService.save(theTask);
			return "redirect:/tasks/list";
		}
		
	}
	
	
	@GetMapping("/delete")
	public String deleteTask(@RequestParam("taskId") int theId) {
		taskService.deleteById(theId);
		return "redirect:/tasks/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("taskId") int theId, Model theModel) {
		
		Task theTask = taskService.findById(theId);
		theModel.addAttribute("task" , theTask);
		return "task-form";
	}
	
	
	//initial theSearchName type is Object. It prevents a potential error when search box is blank.
	@GetMapping("/search")
	public String searchTasks(@RequestParam("search") Object theSearchName, Model theModel) {
		
		String search = String.valueOf(theSearchName);
		search = search.replace(" ", "");
		
		if (search.length() == 0) {
			List<Task> taskList = taskService.findAllByOrderByDueDateAsc();
			theModel.addAttribute("taskList", taskList);
			return "redirect:/tasks/list";
		} else {
			
			List<Task> searchedTaskList = taskService.findByTitleOrDescription(search);
			theModel.addAttribute("taskList", searchedTaskList);
			return "task-list";
		}				
	}
	
		
	
	
	
	//helper method for calculation days remaining to do the task
	private String findDayDifference(Task theTask) {
		String dateString = theTask.getDueDate();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			LocalDate dueDate = LocalDate.parse(dateString, dtf);
			LocalDate today = LocalDate.now();
			int dayDiff = (int) Duration.between(today.atStartOfDay(), dueDate.atStartOfDay()).toDays();
			
			if(dayDiff >= 0) {
				return String.valueOf(dayDiff);
			} else {
				return "Overdue";
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return null;
	}
}
