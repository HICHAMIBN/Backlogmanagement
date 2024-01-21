/**
 * 
 */
package com.example.backlogmanagement.SprintPlaning.Service;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backlogmanagement.SprintPlaning.Entity.Sprint;
import com.example.backlogmanagement.SprintPlaning.Entity.SprintItem;
import com.example.backlogmanagement.SprintPlaning.Repository.SprintItemRepository;
import com.example.backlogmanagement.SprintPlaning.Repository.SprintRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 */
@Service
public class SprintService {

    private final SprintRepository sprintRepository;
    private final SprintItemRepository sprintItemRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository, SprintItemRepository sprintItemRepository) {
        this.sprintRepository = sprintRepository;
        this.sprintItemRepository = sprintItemRepository;
    }

    @RabbitListener(queues = "userStoryToSprintAssignmentQueue")
    public void receiveMessage(String message) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(message);

            if (!jsonNode.has("userStoryId") || !jsonNode.has("sprintId")) {
                throw new IllegalArgumentException("Message does not contain required fields 'userStoryId' and 'sprintId'");
            }

            Long userStoryId = jsonNode.get("userStoryId").asLong();
            Long sprintId = jsonNode.get("sprintId").asLong();

            Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new EntityNotFoundException("Sprint not found"));

            // Erstellen eines neuen SprintItem
            SprintItem sprintItem = new SprintItem();
            sprintItem.setUserStoryId(userStoryId);
            sprintItem.setSprint(sprint);
            sprintItem.setStatus("Assigned"); // oder einen anderen Standardstatus setzen

            sprintItemRepository.save(sprintItem);
        } catch (JsonProcessingException e) {
            System.err.println("Error processing JSON: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing number from JSON: " + e.getMessage());
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }


    // Eine Hilfsklasse zur Repräsentation der Nachricht
    static class UserStoryAssignment {
        private Long userStoryId;
        private Long sprintId;
        
        // Getter und Setter
		
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
    

    // Create
    public Sprint addSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    // Read
    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Sprint getSprintById(Long id) {
        return sprintRepository.findById(id)
        		.orElseThrow(() -> new EntityNotFoundException("Sprint not found"));
    }

    public Sprint updateSprint(Long id, Sprint sprintDetails) {
        Sprint sprint = getSprintById(id);
        sprint.setName(sprintDetails.getName());
        sprint.setStartDate(sprintDetails.getStartDate());
        sprint.setEndDate(sprintDetails.getEndDate());
        return sprintRepository.save(sprint);
    }

    public void deleteSprint(Long id) {
        sprintRepository.deleteById(id);
    }
    
    public void markStoryAsDone(Long sprintItemId) {
        SprintItem sprintItem = sprintItemRepository.findById(sprintItemId)
                .orElseThrow(() -> new EntityNotFoundException("SprintItem not found"));
        sprintItem.setStatus("Done");
        sprintItemRepository.save(sprintItem);
    }
}
