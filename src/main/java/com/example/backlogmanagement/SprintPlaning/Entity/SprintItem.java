package com.example.backlogmanagement.SprintPlaning.Entity;

import jakarta.persistence.*;

@Entity
public class SprintItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintItemId;

    private Long userStoryId;
    private String status;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

	public Long getSprintItemId() {
		return sprintItemId;
	}

	public void setSprintItemId(Long sprintItemId) {
		this.sprintItemId = sprintItemId;
	}

	public Long getUserStoryId() {
		return userStoryId;
	}

	public void setUserStoryId(Long userStoryId) {
		this.userStoryId = userStoryId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

   
    
}
