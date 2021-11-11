package com.adhd.Olivia.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.db.Users;
import com.adhd.Olivia.repo.UserRepository;

@RestController
@RequestMapping("/validation")
public class ValidationController {
	
	@Autowired
	public UserRepository userRepo;
	
	@PostMapping("/login/{login}")
	public ResponseEntity<String> checkLogin(@PathVariable String login){
		System.out.println(login);
		List<Users> loginBasedUsers = userRepo.findByLogin(login);
		if(loginBasedUsers.size()>0) {
			return new ResponseEntity<String>("Login exists",HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("New Login",HttpStatus.OK);		
	}
	
	@PostMapping("/email/{email}")
	public ResponseEntity<String> checkEmail(@PathVariable String email){
		System.out.println(email);
		List<Users> emailBasedUsers = userRepo.findByEmail(email);
		if(emailBasedUsers.size()>0) {
			return new ResponseEntity<String>("Email exists",HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("New Email",HttpStatus.OK);		
	}

}
