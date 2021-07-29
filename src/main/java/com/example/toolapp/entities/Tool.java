package com.example.toolapp.entities;

import javax.persistence.*;

/**
 * A tool available for rental.  Note this defines the basic information about the rental such as the tool type
 * and code.
 *
 * This does not currently define any information about quantity available.  In a 'real' implementation we would likely
 * need to track specific instances of this tool assuming we have multiple available.  We could then stock them in and
 * out as they are rented and returned.  And of course, we can't rent out more than we actually have available.
 * If I were to implement that it would likely be another table that references items from here.
 */
@Entity
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "tool_type_id", referencedColumnName = "id", nullable = false)
    private ToolType toolType;

    @Column(nullable = false)
    private String brand;

    @Column(unique=true, nullable = false)
    private String toolCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }
}