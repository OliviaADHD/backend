package com.adhd.Olivia.models.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class LastPeriodDate {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Menstruation.class)
    @JoinColumn(name = "menstruation_id", nullable = false)
    private Menstruation menstruation;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date lastPeriodStart;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getLastPeriodStart() {
        return lastPeriodStart;
    }

    public void setLastPeriodStart(Date lastPeriodStart) {
        this.lastPeriodStart = lastPeriodStart;
    }

    public Menstruation getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(Menstruation menstruation) {
        this.menstruation = menstruation;
    }
}
