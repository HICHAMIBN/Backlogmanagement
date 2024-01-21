package com.example.backlogmanagement.Backlog.Controller;

import com.example.backlogmanagement.Backlog.Entity.ProductBacklog;
import com.example.backlogmanagement.Backlog.Entity.UserStory;
import com.example.backlogmanagement.Backlog.Service.BacklogService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/backlog")
public class BacklogController {

    private final BacklogService backlogService;

    @Autowired
    public BacklogController(BacklogService backlogService) {
        this.backlogService = backlogService;
    }
    
    @PostMapping("/productbacklog")
    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    public ProductBacklog createProductBacklog(@RequestBody CreateProductBacklogRequest request) {
        return backlogService.createProductBacklog(request.getName(), request.getUserStoryIds());
    }


    @PutMapping("/productbacklog/{productBacklogId}")
    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    public ResponseEntity<?> assignUserStoriesToProductBacklog(@PathVariable Long productBacklogId, @RequestBody List<Long> userStoryIds) {
        backlogService.assignUserStoriesToProductBacklog(productBacklogId, userStoryIds);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    @PostMapping
    public UserStory addUserStory(@RequestBody UserStory userStory) {
        return backlogService.addUserStory(userStory);
    }
    @PreAuthorize("hasAnyRole('ROLE_PRODUCT_OWNER', 'ROLE_TEAM_MEMBER')")
    @GetMapping
    public List<UserStory> getAllUserStory() {
        return backlogService.getAllUserStory();
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PRODUCT_OWNER', 'ROLE_TEAM_MEMBER')")
    @GetMapping("/{id}")
    public UserStory getUserStoryById(@PathVariable Long id) {
        return backlogService.getUserStoryById(id);
    }

    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    @PutMapping("/{id}")
    public UserStory updateUserStory(@PathVariable Long id, @RequestBody UserStory userStoryDetails) {
        return backlogService.updateUserStory(id, userStoryDetails);
    }

    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    @DeleteMapping("/{id}")
    public void deleteUserStory(@PathVariable Long id) {
        backlogService.deleteUserStory(id);
    }
    
    
    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    @PatchMapping("/{id}/priority")
    public ResponseEntity<UserStory> updateUserStoryPriority(@PathVariable Long id, @RequestParam Integer priority) {
       UserStory updatedUserStory = backlogService.updatePriority(id, priority);
       return new ResponseEntity<>(updatedUserStory, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    @PostMapping("/{id}/split")
    public ResponseEntity<List<UserStory>> splitUserStory(@PathVariable Long id, @RequestBody List<UserStory> newStoriesDetails) {
        List<UserStory> splittedStories = backlogService.splitUserStory(id, newStoriesDetails);
        return new ResponseEntity<>(splittedStories, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PRODUCT_OWNER', 'ROLE_TEAM_MEMBER')")
    @PatchMapping("/{id}/sprint")
    public ResponseEntity<UserStory> assignUserStoryToSprint(@PathVariable Long id, @RequestBody Map<String, Long> sprintData) {
        Long sprintId = sprintData.get("sprintId");
        UserStory updatedUserStory = backlogService.assignUserStoryToSprint(id, sprintId);
        return ResponseEntity.ok(updatedUserStory);
    }
    
    @PreAuthorize("hasRole('ROLE_PRODUCT_OWNER')")
    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<UserStory>> getUserStoriesBySprint(@PathVariable Long sprintId) {
        List<UserStory> userStories = backlogService.getUserStoriesBySprintId(sprintId);
        return ResponseEntity.ok(userStories);
    }

    
    
    
}

