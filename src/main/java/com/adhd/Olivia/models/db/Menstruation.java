package com.adhd.Olivia.models.db;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Menstruation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id; //main key of the table, right?

    private Date LastPeriodStart; 
    private Integer PeriodCycleLength; //Note: this involves counting. Might be easier for the user to be able to put two dates in actually :P
    private Integer PeriodLength;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastPeriodStart() {
        return LastPeriodStart;
    }

    public void setLastPeriodStart(Date lastPeriodStart) {
        this.LastPeriodStart = lastPeriodStart;
    }

    public Integer getPeriodCycleLength() {
        return PeriodCycleLength;
    }

    public void setPeriodCycleLength(Integer periodCycleLength) {
        this.PeriodCycleLength = periodCycleLength;
    }

    public Integer getPeriodLength() {
        return PeriodLength;
    }

    public void setPeriodLength(Integer periodLength) {
        this.PeriodLength = periodLength;
    }
}
