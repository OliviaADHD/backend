package com.adhd.Olivia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.Users;
import com.adhd.Olivia.repo.UserRepository;

@RestController
@RequestMapping("/")
public class RegisterController {
	
	@Autowired
	public UserRepository userRepo;
	
	@PostMapping("/signup")
	public ResponseEntity<String> newUser(@RequestBody Users user){
		System.out.println(user.getFullName());
		List<Users> emailBasedUsers = userRepo.findByEmail(user.getEmail());
		List<Users> loginBasedUsers = userRepo.findByLogin(user.getLogin());
		if(emailBasedUsers.size()>0) {
			return new ResponseEntity<String>("Email exists",HttpStatus.FORBIDDEN);
		}
		if(loginBasedUsers.size()>0) {
			return new ResponseEntity<String>("Login exists",HttpStatus.FORBIDDEN);
		}
		userRepo.save(user);
		return new ResponseEntity<String>("Created",HttpStatus.CREATED);		
	}
	
	@PostMapping("/google")
	public ResponseEntity<String> newUserByGoogle(@RequestBody Users user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	@PostMapping("/facebook")
	public ResponseEntity<String> newUserByFacebook(@RequestBody Users user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	@PostMapping("/apple")
	public ResponseEntity<String> newUserByApple(@RequestBody Users user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestBody String email,@RequestBody String password){
		System.out.println(email);
		List<Users> logedInUser = userRepo.findByEmailAndPassword(email, password);
		if(logedInUser.size()==1) {
			return new ResponseEntity<String>("Granted",HttpStatus.OK);		
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
