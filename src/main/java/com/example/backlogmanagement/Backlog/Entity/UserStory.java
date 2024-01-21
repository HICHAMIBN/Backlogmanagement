/**
 * 
 */
package com.example.backlogmanagement.Backlog.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 
 */

@Entity

public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String title;
	    private String description;
	    private String userrole;
	    private String status;
	    private String acceptanceCriteria;
	    private Integer priority;
        private Long sprintId;  

        @ManyToOne
        @JoinColumn(name = "product_backlog_id")
        private ProductBacklog productBacklog;
	   
	    
	    
		public UserStory() {
			
		}
		public UserStory(Long id, String title, String description, String status, String acceptanceCriteria,
				Long sprintId) {
			super();
			this.id = id;
			this.title = title;
			this.description = description;
			this.status = status;
			this.acceptanceCriteria = acceptanceCriteria;
			this.sprintId = sprintId;
			
			}
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getAcceptanceCriteria() {
			return acceptanceCriteria;
		}
		public void setAcceptanceCriteria(String acceptanceCriteria) {
			this.acceptanceCriteria = acceptanceCriteria;
		}
		public Long getSprintId() {
			return sprintId;
		}
		public void setSprintId(Long sprintId) {
			this.sprintId = sprintId;
		}
		public String getUserrole() {
			return userrole;
		}
		public void setUserrole(String userrole) {
			this.userrole = userrole;
		}
		public Integer getPriority() {
			return priority;
		}
		public void setPriority(Integer priority) {
			this.priority = priority;
		}
		public ProductBacklog getProductBacklog() {
	        return productBacklog;
	    }

	    public void setProductBacklog(ProductBacklog productBacklog) {
	        this.productBacklog = productBacklog;
	    }
	}

