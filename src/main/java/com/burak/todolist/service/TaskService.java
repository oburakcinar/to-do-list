package com.burak.todolist.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.burak.todolist.entity.Task;

public interface TaskService {
	
	public List<Task> findAllByOrderByDueDateAsc();
	
	public List<Task> findByTitleOrDescription(String search);
	
	public Task findById(int theId);
	
	public void save(Task theTask);
	
	public void deleteById(int theId);
}
