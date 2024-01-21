package com.example.backlogmanagement.Backlog.Message;

public class UserStoryAssignmentMessage {


	    private Long userStoryId;
	    private Long sprintId;

	    // Konstruktor, Getter und Setter
	    public UserStoryAssignmentMessage(Long userStoryId, Long sprintId) {
	        this.userStoryId = userStoryId;
	        this.sprintId = sprintId;
	    }

	    public Long getUserStoryId() {
	        return userStoryId;
	    }

	    public void setUserStoryId(Long userStoryId) {
	        this.userStoryId = userStoryId;
	    }

	    public Long getSprintId() {
	        return sprintId;
	    }

	    public void setSprintId(Long sprintId) {
	        this.sprintId = sprintId;
	    }
	}

