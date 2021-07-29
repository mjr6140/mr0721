package com.example.toolapp.entities;

import javax.persistence.*;

/**
 * The valid tool types available in the system (Ladder, Chainsaw, etc.)
 */
@Entity
public class ToolType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique=true, nullable = false)
    private String toolType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }
}
