/**
 * 
 */
package com.example.backlogmanagement.SprintPlaning.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backlogmanagement.SprintPlaning.Entity.Sprint;
import com.example.backlogmanagement.SprintPlaning.Service.SprintService;

/**
 * 
 */
@RestController
@RequestMapping("/sprints")
public class SprintController {
    private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

 // POST: Create a new Sprint
    @PostMapping
    public ResponseEntity<Sprint> addSprint(@RequestBody Sprint sprint) {
        Sprint newSprint = sprintService.addSprint(sprint);
        return new ResponseEntity<>(newSprint, HttpStatus.CREATED);
    }

    // GET: Read all Sprints
    @GetMapping
    public List<Sprint> getAllSprints() {
        return sprintService.getAllSprints();
    }

    // GET: Read a single Sprint by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sprint> getSprintById(@PathVariable Long id) {
        Sprint sprint = sprintService.getSprintById(id);
        return new ResponseEntity<>(sprint, HttpStatus.OK);
    }

    // PUT: Update a Sprint
    @PutMapping("/{id}")
    public ResponseEntity<Sprint> updateSprint(@PathVariable Long id, @RequestBody Sprint sprintDetails) {
        Sprint updatedSprint = sprintService.updateSprint(id, sprintDetails);
        return new ResponseEntity<>(updatedSprint, HttpStatus.OK);
    }

    // DELETE: Delete a Sprint
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSprint(@PathVariable Long id) {
        sprintService.deleteSprint(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
