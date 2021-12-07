package com.adhd.Olivia.controllers;

import com.adhd.Olivia.models.db.MainPageTutorial;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.MainPageTutorialRepository;
import com.adhd.Olivia.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/DashboardTutorial")
public class MainPageTutorialController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MainPageTutorialRepository mainPageTutRepo;

    @GetMapping("/checkTutorialDone/{userId}")
    public ResponseEntity<String> checkTutorialDone(@PathVariable String userId){
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
            Optional<MainPageTutorial> tutDone = mainPageTutRepo.findByUser(loginBasedUser);
            return new ResponseEntity<String>("{Exists: " + tutDone.isPresent()+"}", HttpStatus.OK);
        }
    }

    @PutMapping("/setTutorialDone/{userId}")
    public ResponseEntity<String> setTutorialDone(@PathVariable String userId){
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
        } else { // user exists. Register him
            MainPageTutorial mainPageTuto = new MainPageTutorial();
            mainPageTuto.setTutorialDone(true);
            mainPageTuto.setUser(loginBasedUser);

            mainPageTutRepo.save(mainPageTuto);
            return new ResponseEntity<String>("Tutorial done", HttpStatus.OK);
        }
    }

}
