package com.example.backlogmanagement.Backlog;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.backlogmanagement.Backlog.Controller.CreateProductBacklogRequest;
import com.example.backlogmanagement.Backlog.Entity.ProductBacklog;
import com.example.backlogmanagement.Backlog.Entity.UserStory;
import com.example.backlogmanagement.Backlog.Service.BacklogService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BacklogControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private WebApplicationContext context; 
    @MockBean private BacklogService backlogService;
    
    
    @BeforeEach
  public void setUp() {
      mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity()) // Wichtig für Tests mit Spring Security
                .build();
      
  }
  
  
  //Zweisung einer UserStory einem Sprint mit richtigen Daten
  @Test
  @WithMockUser(username="productOwner", roles={"PRODUCT_OWNER"})
  public void assignUserStoryToSprint_ValidData_ReturnsOk() throws Exception {
      
  	// Vorbereiten der gültigen sprintId Daten
      Map<String, Long> validSprintData = new HashMap<>();
      validSprintData.put("sprintId", (long) 5); 

      // Mocking des erwarteten Verhaltens des Services
      UserStory expectedUserStory = new UserStory();
      expectedUserStory.setId((long) 4); 
      
      // Setzen weiterer erwarteter Eigenschaften der UserStory, falls erforderlich
      when(backlogService.assignUserStoryToSprint(4L, 5L)).thenReturn(expectedUserStory);

		// Ausführen der PATCH-Anfrage und Überprüfen der Antwort
      mockMvc.perform(patch("/backlog/4/sprint")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(validSprintData)))
              .andExpect(status().isOk())
              
              // Überprüfen, ob die Antwort die ID der User Story enthält
              .andExpect(jsonPath("$.id").value(expectedUserStory.getId()))
              .andDo(print());// Druckt die gesamte Antwort zur Überprüfung
      		 
  }
  




    



    

}