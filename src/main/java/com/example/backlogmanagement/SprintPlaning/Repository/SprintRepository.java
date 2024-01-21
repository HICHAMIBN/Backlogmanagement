package com.example.backlogmanagement.SprintPlaning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backlogmanagement.SprintPlaning.Entity.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

}
