/**
 * 
 */
package com.example.backlogmanagement.SprintPlaning.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * 
 */
@Entity
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ziel;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate endDate;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SprintItem> sprintItems;

    public Sprint() {
        super();
    }
    
	
	public Sprint(Long id, String name, String ziel, LocalDate startDate, LocalDate endDate, List<Long> userStoryIds) {
	super();
	this.id = id;
	this.name = name;
	this.ziel = ziel;
	this.startDate = startDate;
	this.endDate = endDate;
	
}
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
	public String getZiel() {
		return ziel;
	}
	public void setZiel(String ziel) {
		this.ziel = ziel;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public List<SprintItem> getSprintItems() {
        return sprintItems;
    }

    public void setSprintItems(List<SprintItem> sprintItems) {
        this.sprintItems = sprintItems;
    }
    
    
    
}
