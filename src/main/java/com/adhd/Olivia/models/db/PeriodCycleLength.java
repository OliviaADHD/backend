package com.adhd.Olivia.models.db;

import javax.persistence.*;

@Entity
public class PeriodCycleLength {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Menstruation.class)
    @JoinColumn(name = "menstruation_id", nullable = false)
    private Menstruation menstruation;

    private int periodCycleLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriodCycleLength() {
        return periodCycleLength;
    }

    public void setPeriodCycleLength(int periodCycleLength) {
        this.periodCycleLength = periodCycleLength;
    }

    public Menstruation getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(Menstruation menstruation) {
        this.menstruation = menstruation;
    }
}
