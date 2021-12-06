package com.adhd.Olivia.models.db;

import com.adhd.Olivia.enums.AgeGroup;
import com.adhd.Olivia.enums.RegularMenstruation;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Menstruation {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "first_time")
    private boolean firstTime;

    @Column(name = "regular")
    @Enumerated(EnumType.ORDINAL)
    private RegularMenstruation regular;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy="menstruation", targetEntity = PeriodLength.class)
    private List<PeriodLength> periodLengths = new ArrayList<>();

    @OneToMany(mappedBy="menstruation", targetEntity = PeriodCycleLength.class)
    private List<PeriodCycleLength> periodCycleLengths = new ArrayList<>();


    @OneToMany(mappedBy="menstruation", cascade = CascadeType.ALL, targetEntity = LastPeriodDate.class)
    private List<LastPeriodDate> lastPeriodStarts = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public RegularMenstruation getRegular() {
        return regular;
    }

    public void setRegular(RegularMenstruation regular) {
        this.regular = regular;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PeriodLength> getPeriodLengths() {
        return periodLengths;
    }

    public void addPeriodLength(PeriodLength periodLength){
        periodLengths.add(periodLength);
    }

    public void addPeriodCycleLengths(PeriodCycleLength periodCycleLength){
        periodCycleLengths.add(periodCycleLength);
    }

    public void addLastPeriodStarts(LastPeriodDate periodDate){
        lastPeriodStarts.add(periodDate);
    }

    public void setPeriodLengths(List<PeriodLength> periodLengths) {
        this.periodLengths = periodLengths;
    }

    public List<PeriodCycleLength> getPeriodCycleLengths() {
        return periodCycleLengths;
    }

    public void setPeriodCycleLengths(List<PeriodCycleLength> periodCycleLengths) {
        this.periodCycleLengths = periodCycleLengths;
    }

    public List<LastPeriodDate> getLastPeriodStarts() {
        return lastPeriodStarts;
    }

    public void setLastPeriodStarts(List<LastPeriodDate> lastPeriodStarts) {
        this.lastPeriodStarts = lastPeriodStarts;
    }
}
