// Definieren des Service-Pakets und Importieren der benötigten Klassen und Pakete.
package com.example.backlogmanagement.Backlog.Service;

import com.example.backlogmanagement.Backlog.Entity.ProductBacklog;
import com.example.backlogmanagement.Backlog.Entity.UserStory;
import com.example.backlogmanagement.Backlog.Repository.ProductBacklogRepository;
import com.example.backlogmanagement.Backlog.Repository.UserStoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // Markiert diese Klasse als Spring Service-Komponente.
public class BacklogService {
	
	// Deklaration von Abhängigkeiten: Repository für UserStory-Objekte und RabbitTemplate für Messaging.
    private final UserStoryRepository userStoryRepository;
    private final ProductBacklogRepository productBacklogRepository;
    private final RabbitTemplate rabbitTemplate;
    
    // Konstante für den Namen der RabbitMQ-Queue.
    private static final String ASSIGNMENT_QUEUE = "userStoryToSprintAssignmentQueue";
    
    // Konstruktor mit Autowired-Annotation, um Abhängigkeiten zu injizieren.
    @Autowired
    public BacklogService(UserStoryRepository userStoryRepository, ProductBacklogRepository productBacklogRepository, RabbitTemplate rabbitTemplate) {
        this.userStoryRepository = userStoryRepository;
        this.productBacklogRepository = productBacklogRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    public UserStory assignUserStoryToSprint(Long userStoryId, Long sprintId) {
        UserStory userStory = findUserStoryById(userStoryId);
        userStory.setSprintId(sprintId);
        UserStory updatedUserStory = userStoryRepository.save(userStory);

        // Senden der Nachricht
        String message = createAssignmentMessage(userStory);
        rabbitTemplate.convertAndSend(ASSIGNMENT_QUEUE, message);

        return updatedUserStory;  // Rückgabe des aktualisierten User Story-Objekts
    }




        // Erstellen und Senden einer Nachricht an die RabbitMQ-Queue.
        private String createAssignmentMessage(UserStory userStory) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(new UserStoryAssignment(userStory.getId(), userStory.getSprintId()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error creating assignment message", e);
            }
        }

        // Eine Hilfsklasse zur Repräsentation der Nachricht
        static class UserStoryAssignment {
            private Long userStoryId;
            private Long sprintId;

            public UserStoryAssignment(Long userStoryId, Long sprintId) {
                this.userStoryId = userStoryId;
                this.sprintId = sprintId;
            }

			public Long getUserStoryId() {
				return userStoryId;
			}

			public Long getSprintId() {
				return sprintId;
			}

			public void setSprintId(Long sprintId) {
				this.sprintId = sprintId;
			}
            
    }  
      //Asychrone Kommunikation mit Kafka
        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        public UserStory assignUserStoryToSprint1(Long userStoryId, Long sprintId) {
            UserStory userStory = findUserStoryById(userStoryId);
            userStory.setSprintId(sprintId);
            UserStory updatedUserStory = userStoryRepository.save(userStory);

            // Senden der Nachricht
            String message = createAssignmentMessage(userStory);
            kafkaTemplate.send("userStoryToSprintAssignmentTopic", message);


            return updatedUserStory;
        }

        private String createAssignmentMessage1(UserStory userStory) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(new UserStoryAssignment(userStory.getId(), userStory.getSprintId()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error creating assignment message", e);
            }
        } 
        // Methode zum Erstellen eines neuen ProductBacklogs und Hinzufügen von User Stories
        public ProductBacklog createProductBacklog(String name, List<Long> userStoryIds) {
            ProductBacklog productBacklog = new ProductBacklog();
            productBacklog.setName(name);
            List<UserStory> userStories = userStoryIds.stream()
                    .map(this::findUserStoryById)
                    .collect(Collectors.toList());
            productBacklog.setUserStories(userStories);
            productBacklogRepository.save(productBacklog);
            return productBacklogRepository.save(productBacklog);
        }
        
        // Methode zum Zuweisen von User Stories zu einem bestimmten ProductBacklog
        public void assignUserStoriesToProductBacklog(Long productBacklogId, List<Long> userStoryIds) {
            ProductBacklog productBacklog = productBacklogRepository.findById(productBacklogId)
                    .orElseThrow(() -> new EntityNotFoundException("ProductBacklog not found"));
            List<UserStory> userStories = userStoryIds.stream()
                    .map(this::findUserStoryById)
                    .collect(Collectors.toList());
            userStories.forEach(story -> {
                story.setProductBacklog(productBacklog);
                userStoryRepository.save(story);
            });
        }
        
        // Eine Anfrage, die alle User Stories zurückgibt, die zu einem bestimmten Sprint gehören
        public List<UserStory> getUserStoriesBySprintId(Long sprintId) {
            return userStoryRepository.findBySprintId(sprintId);
        }
    
	    // Hilfsmethode, um eine User Story anhand ihrer ID zu finden.
	    private UserStory findUserStoryById(Long id) {
	        return userStoryRepository.findById(id)
	                .orElseThrow(() -> new EntityNotFoundException("User Story not found"));
    }
		 // CRUD-Operationen für User Stories.
	    public UserStory addUserStory(UserStory userStory) {
	        
	        return userStoryRepository.save(userStory);
	    }
	    public  List<UserStory> getAllUserStory() {
			
			return userStoryRepository.findAll();
		}
	
	    public UserStory getUserStoryById(Long id) {
	        return userStoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("User Story not found"));
	    }
	
	    public UserStory updateUserStory(Long id, UserStory userStoryDetails) {
	        UserStory userStory = userStoryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("User Story not found"));
	        
	        // Aktualisieren der Eigenschaften der User Story.
	        userStory.setTitle(userStoryDetails.getTitle());
	        userStory.setDescription(userStoryDetails.getDescription());
	        userStory.setUserrole(userStoryDetails.getUserrole());
	        userStory.setStatus(userStoryDetails.getStatus());
	        userStory.setAcceptanceCriteria(userStoryDetails.getAcceptanceCriteria());
	        userStory.setPriority(userStoryDetails.getPriority());
	       
	
	        return userStoryRepository.save(userStory);
	    }
	    	public void deleteUserStory(Long id) {
	    		UserStory userStory = userStoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Story not found"));
	    		userStoryRepository.delete(userStory);
    }
    		// Methoden zur Aktualisierung der Priorität.
    		public UserStory updatePriority(Long id, Integer newPriority) {
    			UserStory userStory = userStoryRepository.findById(id)
    					.orElseThrow(() -> new EntityNotFoundException("User Story not found"));
    					userStory.setPriority(newPriority);
    					return userStoryRepository.save(userStory);
    }
    		
    		// Methode zum Aufteilen einer User Story in mehrere neue User Stories.
    		public List<UserStory> splitUserStory(Long id, List<UserStory> newStoriesDetails) {
    					UserStory originalStory = userStoryRepository.findById(id)
    					.orElseThrow(() -> new EntityNotFoundException("User Story not found"));

        List<UserStory> newStories = new ArrayList<>();
        for (UserStory details : newStoriesDetails) {
            UserStory newStory = new UserStory();
            
            // Setzen der Eigenschaften für jede neue User Story basierend auf den Details.
            newStory.setTitle(details.getTitle());
            newStory.setDescription(details.getDescription());
            newStory.setUserrole(details.getUserrole());
            newStory.setStatus(details.getStatus());
            newStory.setAcceptanceCriteria(details.getAcceptanceCriteria());
            newStory.setPriority(details.getPriority());
            newStories.add(newStory);
        }

        // Speichern der neuen User Stories in der Datenbank und Rückgabe der Liste.
        	return newStories.stream()
            .map(userStoryRepository::save)
            .collect(Collectors.toList());
    }
    
    



}
