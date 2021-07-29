package com.example.toolapp.entities;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The rental charge information for each type of tool.
 */
@Entity
public class RentalCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tool_type_id", referencedColumnName = "id")
    private ToolType toolType;

    @Column(nullable = false)
    private BigDecimal dailyCharge;

    @Column(nullable = false)
    private boolean weekdayCharge;

    @Column(nullable = false)
    private boolean weekendCharge;

    @Column(nullable = false)
    private boolean holidayCharge;

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

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void setDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean isHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }
}
