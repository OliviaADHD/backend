package com.adhd.Olivia.controllers;


import com.adhd.Olivia.enums.RegularMenstruation;
import com.adhd.Olivia.models.db.*;
import com.adhd.Olivia.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping(path="/menstruation")
public class MenstruationController {
    @Autowired
    private MenstruationRepository menstruationRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PeriodLengthRepository periodLengthRepo;
    @Autowired
    private PeriodCycleLengthRepository periodCycleLengthRepo;
    @Autowired
    private LastPeriodDateRepository lastPeriodDateRepo;

    @GetMapping("/checkUserMenstruationDataExists/{userId}")
    public ResponseEntity<String> getUserMenstruationData(@PathVariable String userId){
        System.out.println("Put Data for " + userId);
        int id = 0;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST);
        }
        //check whether the user exists:
        User loginBasedUser = userRepo.findById(id);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else { // user exists. First time he registers?
            Optional<Menstruation> menstruationUser = menstruationRepo.findByUser(loginBasedUser);
            return new ResponseEntity<String>("{Exists: "+menstruationUser.isPresent()+"}", HttpStatus.OK);

        }
    }

    @GetMapping("/getUserData/{userId}")
    public ResponseEntity<String> getUserData(@PathVariable String userId){
        System.out.println("check data for "+ userId);
        int id = 0;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST);
        }
        User loginBasedUser = userRepo.findById(id);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            Optional<Menstruation> menstruationUser = menstruationRepo.findByUser(loginBasedUser);
            if (!menstruationUser.isPresent()) {
                return new ResponseEntity<String>("No data entered for this user", HttpStatus.NOT_FOUND);
            } else {
                Menstruation menstru = menstruationUser.get();
                List<PeriodCycleLength> pCL = periodCycleLengthRepo.findByMenstruation(menstru);
                List<PeriodLength> pL = periodLengthRepo.findByMenstruation(menstru);
                List<LastPeriodDate> lPD = lastPeriodDateRepo.findByMenstruation(menstru);

                String responsePCL = "";
                for (PeriodCycleLength p: pCL){
                    responsePCL = responsePCL + p.getPeriodCycleLength() + ", ";
                }
                responsePCL = responsePCL.substring(0,responsePCL.length()-2);

                String responsePL = "";
                for (PeriodLength p: pL){
                    responsePL = responsePL + p.getPeriodLength() + ", ";
                }
                String responseLPD = "";
                responsePL = responsePL.substring(0, responsePL.length()-2);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                for (LastPeriodDate p: lPD){
                    responseLPD = responseLPD + "\""+formatter.format(p.getLastPeriodStart()) + "\""+", ";
                }
                responseLPD = responseLPD.substring(0, responseLPD.length()-2);

                String response = "{ \"regular\":" + true+
                        ", \"firstTime\":" + menstru.isFirstTime() +
                        ", \"periodCycleLengths\": [" + responsePCL + "]" +
                        ", \"PeriodLengths\": [" + responsePL + "]" +
                        ", \"LastPeriodStarts\": [" + responseLPD + "]" +
                        "}";
                return new ResponseEntity<String>(response,HttpStatus.OK);
            }
        }
    }

    @PutMapping("/setUserDataFirstTime")
    public ResponseEntity<String> setUserDataFirstTime(@RequestBody Map<String, String> json){

        if ((!json.containsKey("lastPeriodStart")) || (!json.containsKey("userId")) ||
                (!json.containsKey("periodLength")) || (!json.containsKey("periodCycleLength")) ||
                (!json.containsKey("regular"))) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Put Data for " + json.toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date parsed;
        try {
            parsed = formatter.parse(json.get("lastPeriodStart"));
        }
        catch (ParseException e){
            return new ResponseEntity<String>("not a valid date. Should be of format dd/MM/yyyy", HttpStatus.BAD_REQUEST);
        }
        Date lastPeriodStart = new Date(parsed.getTime());
        //check whether the user exists:
        int id = 0;
        try{ id = Integer.parseInt(json.get("userId")); }
        catch (NumberFormatException e) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST); }
        User loginBasedUser = userRepo.findById(id);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else { // user exists. Is it his first time?
            Optional<Menstruation> menstruationUser = menstruationRepo.findByUser(loginBasedUser);
            if (menstruationUser.isPresent()){ //no!
                return new ResponseEntity<String>("Not first time", HttpStatus.FORBIDDEN);
            } else {
                Menstruation firstMenstruation = new Menstruation();
                firstMenstruation.setFirstTime(false);
                firstMenstruation.setRegular(RegularMenstruation.getById(Integer.parseInt(json.get("regular"))));
                firstMenstruation.setUser(loginBasedUser);

                PeriodLength periodLength = new PeriodLength();
                periodLength.setPeriodLength(Integer.parseInt(json.get("periodLength")));
                periodLength.setMenstruation(firstMenstruation);

                PeriodCycleLength periodCycleLength = new PeriodCycleLength();
                periodCycleLength.setPeriodCycleLength(Integer.parseInt(json.get("periodCycleLength")));
                periodCycleLength.setMenstruation(firstMenstruation);

                LastPeriodDate lastPeriodDate = new LastPeriodDate();
                lastPeriodDate.setLastPeriodStart(lastPeriodStart);
                lastPeriodDate.setMenstruation(firstMenstruation);

                firstMenstruation.addPeriodCycleLengths(periodCycleLength);
                firstMenstruation.addPeriodLength(periodLength);
                firstMenstruation.addLastPeriodStarts(lastPeriodDate);
                menstruationRepo.save(firstMenstruation);
                periodLengthRepo.save(periodLength);
                periodCycleLengthRepo.save(periodCycleLength);
                lastPeriodDateRepo.save(lastPeriodDate);
                return new ResponseEntity<String>("Created", HttpStatus.CREATED);
            }
        }
    }



    @PutMapping("/additionalStartPeriod")
    public ResponseEntity<String> updateStartPeriod(@RequestBody Map<String, String> json) {
        System.out.println("updating the period cycle" + json.toString());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date parsed;
        try {
            parsed = formatter.parse(json.get("lastPeriodStart"));
        }
        catch (ParseException e){
            return new ResponseEntity<String>("not a valid date. Should be of format dd/MM/yyyy", HttpStatus.BAD_REQUEST);
        }
        Date lastPeriodStart = new Date(parsed.getTime());

        //check whether the user exists:
        int id = 0;
        try{ id = Integer.parseInt(json.get("userId")); }
        catch (NumberFormatException e) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST); }
        User loginBasedUser = userRepo.findById(id);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else { // user exists. Is it his first time?
            Optional<Menstruation> menstruationUser = menstruationRepo.findByUser(loginBasedUser);
            if (!menstruationUser.isPresent()){
                return new ResponseEntity<String>("First time", HttpStatus.FORBIDDEN);
            } else {
                Menstruation menstru = menstruationUser.get();
                LastPeriodDate lastPeriodDate = new LastPeriodDate();
                lastPeriodDate.setLastPeriodStart(lastPeriodStart);
                lastPeriodDate.setMenstruation(menstru);

                lastPeriodDateRepo.save(lastPeriodDate);
                return new ResponseEntity<String>("Added Date",HttpStatus.CREATED);
            }
        }
    }


    @PutMapping("/additionalPeriodCycleLength")
    public ResponseEntity<String> updateCycleLength(@RequestBody Map<String, String> json) {
        System.out.println("updating the cycle length" + json.toString());

        //check whether the user exists:
        int id = 0;
        try{ id = Integer.parseInt(json.get("userId")); }
        catch (NumberFormatException e) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST); }
        User loginBasedUser = userRepo.findById(id);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else { // user exists. Is it his first time?
            Optional<Menstruation> menstruationUser = menstruationRepo.findByUser(loginBasedUser);
            if (!menstruationUser.isPresent()){
                return new ResponseEntity<String>("First time", HttpStatus.FORBIDDEN);
            } else {
                Menstruation menstru = menstruationUser.get();
                PeriodCycleLength periodCycleLength = new PeriodCycleLength();
                periodCycleLength.setPeriodCycleLength(Integer.parseInt(json.get("periodCycleLength")));
                periodCycleLength.setMenstruation(menstru);

                periodCycleLengthRepo.save(periodCycleLength);
                return new ResponseEntity<String>("Added",HttpStatus.CREATED);
            }
        }
    }

    @PutMapping("/additionalPeriodLength")
    public ResponseEntity<String> updatePeriodLength(@RequestBody Map<String, String> json) {
        System.out.println("updating the cycle length" + json.toString());

        //check whether the user exists:
        int id = 0;
        try{ id = Integer.parseInt(json.get("userId")); }
        catch (NumberFormatException e) {
            return new ResponseEntity<String>("invalid input", HttpStatus.BAD_REQUEST); }
        User loginBasedUser = userRepo.findById(id);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else { // user exists. Is it his first time?
            Optional<Menstruation> menstruationUser = menstruationRepo.findByUser(loginBasedUser);
            if (!menstruationUser.isPresent()){
                return new ResponseEntity<String>("First time", HttpStatus.FORBIDDEN);
            } else {
                Menstruation menstru = menstruationUser.get();
                PeriodLength periodLength = new PeriodLength();
                periodLength.setPeriodLength(Integer.parseInt(json.get("periodLength")));
                periodLength.setMenstruation(menstru);

                periodLengthRepo.save(periodLength);
                return new ResponseEntity<String>("Added",HttpStatus.CREATED);
            }
        }
    }

}
