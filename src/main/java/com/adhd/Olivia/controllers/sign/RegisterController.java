package com.adhd.Olivia.controllers.sign;

import java.io.IOException;
import java.util.*;

import javax.mail.MessagingException;

import com.adhd.Olivia.models.db.FacebookUser;
import com.adhd.Olivia.repo.FacebookUserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

import com.adhd.Olivia.models.db.Profile;
import com.adhd.Olivia.models.db.Questionarrie;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.ProfileRepository;
import com.adhd.Olivia.repo.QuestionarrieRepo;
import com.adhd.Olivia.repo.UserRepository;
import com.adhd.Olivia.services.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.adhd.Olivia.custom.MailTypes;




@RestController
@RequestMapping("/user")
public class RegisterController {
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public QuestionarrieRepo questionRepo;

	@Autowired
	public FacebookUserRepository fbRepo;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	public ProfileRepository profileRepo;
    
	@PostMapping("/signup")
	public ResponseEntity<String> newUser(@RequestBody User user) throws MessagingException, JsonProcessingException{
		List<User> emailBasedUsers = userRepo.findByEmail(user.getEmail());
		List<User> loginBasedUsers = userRepo.findByLogin(user.getLogin());
		if(emailBasedUsers.size()>0) {
			return new ResponseEntity<String>("Email exists",HttpStatus.FORBIDDEN);
		}
		if(loginBasedUsers.size()>0) {
			return new ResponseEntity<String>("Login exists",HttpStatus.FORBIDDEN);
		}
		userRepo.save(user);
		Map<String,Object> response = new HashMap<>();
		response.put("userId",user.getId());
		response.put("name",user.getFullName());
		Profile newProfile = new Profile();
		newProfile.setUser(user);
		profileRepo.save(newProfile);
		String json = new ObjectMapper().writeValueAsString(response);
		//mailService.sendEmail(MailTypes.signUp(user.getEmail()));
		return new ResponseEntity<String>(json,HttpStatus.CREATED);		
	}
	

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody String json) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> map = mapper.readValue(json, Map.class);
            String email = map.get("email");
            String password = map.get("password");
			if (password.equals("google")) {
				return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
			}
			if (password.equals("facebook")){
				return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
			}
    		List<User> logedInUser = userRepo.findByEmailAndPassword(email, password);
    		if(logedInUser.size()==1) {			
    			Map<String,Object> response = new HashMap<>();
    			response.put("userId",logedInUser.get(0).getId());
    			response.put("name",logedInUser.get(0).getFullName());
    			Optional<Questionarrie> firstTime = questionRepo.findByUser(logedInUser.get(0));
    			if(firstTime.isPresent()) {
    				response.put("firstTime",false);
    			}else {
    				response.put("firstTime",true);
    			}
    			Optional<Profile> userProfile = profileRepo.findByUser(logedInUser.get(0));
    			if(userProfile.isEmpty()) {
    				return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
    			}
    			Profile prof = userProfile.get();
    			response.put("language", prof.getLanguage().getDescription());
    			response.put("darkMode", prof.isDarkMode());
    			response.put("hidePhoto", prof.isHidePhoto());
    			response.put("stopNotification", prof.isStopNotification());
    			response.put("tutorialCompleted", prof.isTutorialCompleted());

    			String responseJson = new ObjectMapper().writeValueAsString(response);
    			return new ResponseEntity<String>(responseJson,HttpStatus.OK);		
    		}else {
    			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
    		}
        } catch (IOException e) {
        	return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }		
	}
	
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> reset(@RequestBody String json) throws MessagingException, JsonMappingException, JsonProcessingException{		
		ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(json, Map.class);
        String email = map.get("email");
        System.out.println("Reset Password"+email);
		List<User> emailBasedUsers = userRepo.findByEmail(email);
		if(emailBasedUsers.size()==0) {
			return new ResponseEntity<String>("Email doesn't exist",HttpStatus.FORBIDDEN);
		}
		else {
			User usr = emailBasedUsers.get(0);			
			String randomPassword = RandomStringUtils.randomAlphabetic(10);
			usr.setPassword(randomPassword);
			userRepo.save(usr);
			mailService.sendEmail(MailTypes.resetPassword(usr.getEmail(),randomPassword));
			return new ResponseEntity<String>("OK",HttpStatus.OK);
		}			
	}
	

	

}
