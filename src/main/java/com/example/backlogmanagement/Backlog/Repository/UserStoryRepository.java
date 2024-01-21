/**
 * 
 */
package com.example.backlogmanagement.Backlog.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backlogmanagement.Backlog.Entity.UserStory;

/**
 * 
 */
public interface UserStoryRepository extends JpaRepository<UserStory, Long> {
	
	List<UserStory> findBySprintId(Long sprintId);
	
}