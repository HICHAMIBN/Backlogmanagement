package com.example.backlogmanagement.Backlog.Controller;

import java.util.List;

public class CreateProductBacklogRequest {

	private String name;
    private List<Long> userStoryIds;
	public String getName() {
		return name;
	

	}

	public CreateProductBacklogRequest() {
		super();
	}

	public CreateProductBacklogRequest(String name, List<Long> userStoryIds) {
		super();
		this.name = name;
		this.userStoryIds = userStoryIds;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<Long> getUserStoryIds() {
		return userStoryIds;
	}
	public void setUserStoryIds(List<Long> userStoryIds) {
		this.userStoryIds = userStoryIds;
	}

    
}
