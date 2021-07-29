package com.example.toolapp.repositories;

import com.example.toolapp.entities.Tool;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    Tool findByToolCode(String toolCode);

}