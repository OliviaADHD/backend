package com.adhd.Olivia.models.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Menstruation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id; //main key of the table, right? same as user id?

    private String login; //in order to have it the same as in the user table

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date lastPeriodStart;

    private int periodCycleLength; //Note: this involves counting. Might be easier for the user to be able to put two dates in actually :P
    private int periodLength;

    private String regular;

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

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

    public int getPeriodCycleLength() {
        return periodCycleLength;
    }

    public void setPeriodCycleLength(int periodCycleLength) {
        this.periodCycleLength = periodCycleLength;
    }

    public int getPeriodLength() {
        return periodLength;
    }

    public void setPeriodLength(int periodLength) {
        this.periodLength = periodLength;
    }
}
