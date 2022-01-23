package com.burak.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.burak.todolist.dao.TaskRepository;
import com.burak.todolist.entity.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	@Override
	public List<Task> findAllByOrderByDueDateAsc() {
		return taskRepository.findAllByOrderByDueDateAsc();
	}
	
	public List<Task> findByTitleOrDescription(String search) {
		return taskRepository.findByTitleOrDescription(search);
	}

	@Override
	public Task findById(int theId) {
		
		Optional<Task> result = taskRepository.findById(theId);
		
		
		
		if(result.isPresent()) {
			return result.get();
		} else {
			throw new RuntimeException("Cannot find the task with the id " + theId);
		}
		
	}

	@Override
	public void save(Task theTask) {
		taskRepository.save(theTask);

	}

	@Override
	public void deleteById(int theId) {
		taskRepository.deleteById(theId);

	}

}
