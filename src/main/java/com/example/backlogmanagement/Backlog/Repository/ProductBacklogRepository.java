package com.example.backlogmanagement.Backlog.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backlogmanagement.Backlog.Entity.ProductBacklog;

public interface ProductBacklogRepository extends JpaRepository<ProductBacklog, Long> {
    // Benutzerdefinierte Abfragen nach Bedarf
}