package com.burak.todolist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="todo_list")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@NotBlank(message="is required") 
	@Size(min=1, message="is required")
	@Column(name="title")
	private String title;
	
	@NotBlank(message="is required") 
	@Size(min=1, message="is required")
	@Column(name="description")
	private String description;
	
	@NotNull(message="is required")
	@Column(name="due_date")
	private String dueDate;
	
	@Column(name="days_remaining")
	private String daysRemaining;
	

	public Task() {
		
	}

	public Task(int id, String title, String description, String dueDate, String daysRemaining) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.daysRemaining = daysRemaining;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	
	public String getDaysRemaining() {
		return daysRemaining;
	}

	public void setDaysRemaining(String daysRemaining) {
		this.daysRemaining = daysRemaining;
	}
	
	
}
