package com.adhd.Olivia.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.db.Questionarrie;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.QuestionarrieRepo;
import com.adhd.Olivia.repo.UserRepository;
import com.adhd.Olivia.services.MailService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	private MailService mailService;
    
	@PostMapping("/signup")
	public ResponseEntity<String> newUser(@RequestBody User user) throws MessagingException, JsonProcessingException{
		System.out.println(user.getFullName());
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
		String json = new ObjectMapper().writeValueAsString(response);
		mailService.sendEmail(MailTypes.signUp(user.getEmail()));
		return new ResponseEntity<String>(json,HttpStatus.CREATED);		
	}
	
	
	@PostMapping("/reset-password")
	public ResponseEntity<String> reset(@RequestBody String email) throws MessagingException{
		System.out.println(email);
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
	
	
	@PostMapping("/google")
	public ResponseEntity<String> newUserByGoogle(@RequestBody User user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	@PostMapping("/facebook")
	public ResponseEntity<String> newUserByFacebook(@RequestBody User user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	@PostMapping("/apple")
	public ResponseEntity<String> newUserByApple(@RequestBody User user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody String json) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> map = mapper.readValue(json, Map.class);
            String email = map.get("email");
            String password = map.get("password");
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
    			String responseJson = new ObjectMapper().writeValueAsString(response);
    			return new ResponseEntity<String>(responseJson,HttpStatus.OK);		
    		}else {
    			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
    		}
        } catch (IOException e) {
        	return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }		
	}
	
	@GetMapping("/google")
	public ResponseEntity<String> loginByGoogle(@RequestBody String email,@RequestBody String password){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);			
	}
	
	@GetMapping("/facebook")
	public ResponseEntity<String> loginByFacebook(@RequestBody String email,@RequestBody String password){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);			
	}
	
	@GetMapping("/apple")
	public ResponseEntity<String> loginByApple(@RequestBody String email,@RequestBody String password){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);			
	}
	

}
