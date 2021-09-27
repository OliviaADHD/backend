package com.adhd.Olivia.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.Users;
import com.adhd.Olivia.repo.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	public UserRepository userRepo;
	
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody Users user){
		System.out.println(user.getName());
		Optional<Users> optionalNewUser = userRepo.findById(user.getId());
		if(optionalNewUser.isPresent()) {
			Users newUser = optionalNewUser.get();
			newUser.setEmail(user.getEmail());
			newUser.setPassword(user.getPassword());
			newUser.setLogin(user.getLogin());
			userRepo.save(newUser);
		}else {
			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);	
		}
		userRepo.save(user);
		return new ResponseEntity<String>("Created",HttpStatus.CREATED);		
	}

}
