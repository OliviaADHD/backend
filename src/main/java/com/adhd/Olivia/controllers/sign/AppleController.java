package com.adhd.Olivia.controllers.sign;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.db.User;


@RestController
@RequestMapping("/user")
public class AppleController {

	@PostMapping("/apple")
	public ResponseEntity<String> newUserByApple(@RequestBody User user){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);		
	}
	
	@GetMapping("/apple")
	public ResponseEntity<String> loginByApple(@RequestBody String email,@RequestBody String password){
		return new ResponseEntity<String>("Waiting for development",HttpStatus.TEMPORARY_REDIRECT);			
	}
}
