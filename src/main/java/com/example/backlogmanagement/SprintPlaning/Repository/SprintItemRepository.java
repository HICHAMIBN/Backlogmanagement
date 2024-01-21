package com.example.backlogmanagement.SprintPlaning.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backlogmanagement.SprintPlaning.Entity.SprintItem;

public interface SprintItemRepository extends JpaRepository<SprintItem, Long> {

}
