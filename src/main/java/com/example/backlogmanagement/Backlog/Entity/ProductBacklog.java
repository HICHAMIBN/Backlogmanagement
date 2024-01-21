package com.example.backlogmanagement.Backlog.Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "productBacklog")
    private List<UserStory> userStories = new ArrayList<>();

    public ProductBacklog() {}

    public ProductBacklog(String name, List<UserStory> userStories) {
		super();
		this.name = name;
		this.userStories = userStories;
	}

	// Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }

    public void addUserStory(UserStory userStory) {
        userStories.add(userStory);
        userStory.setProductBacklog(this);
    }
}