package com.serethewind.taskmanager.repository;

import com.serethewind.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("""
            select t from Task t
            where upper(t.name) like upper(concat('%', :query, '%')) or upper(t.description) like upper(concat('%', :query, '%'))""")
    List<Task> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String query);
}
