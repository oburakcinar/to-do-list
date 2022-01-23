package com.burak.todolist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.burak.todolist.entity.Task;


public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	public List<Task> findAllByOrderByDueDateAsc();
	
	//list for search option
	@Query("select t from Task t where lower(t.title) like lower(concat('%', :search, '%')) " +
			"or lower(t.description) like lower(concat('%', :search, '%'))")
	public List<Task> findByTitleOrDescription(@Param("search") String search);
}
