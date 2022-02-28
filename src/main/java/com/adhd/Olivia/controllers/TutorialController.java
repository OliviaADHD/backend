package com.adhd.Olivia.controllers;

import com.adhd.Olivia.models.db.Profile;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.ProfileRepository;
import com.adhd.Olivia.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tutorial")
public class TutorialController {
    
	@Autowired
    private UserRepository userRepo;
	
	@Autowired
	private ProfileRepository profRepo;

    @PutMapping("/{userId}")
    public ResponseEntity<String> tutorialCompleted(@PathVariable int userId){
        //check whether the user exists:
        User loginBasedUser = userRepo.findById(userId);
        if(loginBasedUser == null) {
            return new ResponseEntity<String>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else { // user exists. Check if already registered
        	Optional<Profile> optProf = profRepo.findByUser(loginBasedUser);
        	if(optProf.isEmpty()) {
        		return new ResponseEntity<String>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        	}
        	Profile prof = optProf.get();
        	prof.setTutorialCompleted(true);
        	profRepo.save(prof);
        	return new ResponseEntity<String>("Tutorial done", HttpStatus.OK);
        }
    }

}
