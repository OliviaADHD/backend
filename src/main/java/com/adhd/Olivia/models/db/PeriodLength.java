package com.adhd.Olivia.models.db;

import javax.persistence.*;

@Entity
public class PeriodLength {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne(targetEntity = Menstruation.class)
    @JoinColumn(name = "menstruation_id", nullable = false)
    private Menstruation menstruation;

    private int periodLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }

    public Menstruation getMenstruation() {
        return menstruation;
    }

    public void setMenstruation(Menstruation menstruation) {
        this.menstruation = menstruation;
    }

    public PeriodLength() {
    }
}
