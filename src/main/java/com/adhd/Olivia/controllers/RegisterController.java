package com.adhd.Olivia.controllers;

import java.util.List;

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

import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.models.response.Login;
import com.adhd.Olivia.repo.UserRepository;
import com.adhd.Olivia.services.MailService;
import com.adhd.Olivia.custom.MailTypes;


@RestController
@RequestMapping("/")
public class RegisterController {
	
	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	private MailService mailService;
    
	@PostMapping("/signup")
	public ResponseEntity<String> newUser(@RequestBody User user) throws MessagingException{
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
		mailService.sendEmail(MailTypes.signUp(user.getEmail()));
		return new ResponseEntity<String>("Created",HttpStatus.CREATED);		
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
	
	
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login signIn){
		System.out.println(signIn.getEmail());
		List<User> logedInUser = userRepo.findByEmailAndPassword(signIn.getEmail(), signIn.getPassword());
		if(logedInUser.size()==1) {
			String response = "{ 'userId':"+logedInUser.get(0).getId()+", 'name':"+logedInUser.get(0).getFullName()+"}";
			return new ResponseEntity<String>(response,HttpStatus.OK);		
		}else {
			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
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
