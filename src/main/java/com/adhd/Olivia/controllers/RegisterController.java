package com.adhd.Olivia.controllers;

import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;

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
		//mailService.sendEmail(MailTypes.signUp(user.getEmail()));
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
	
	
	@PostMapping("/signup-google")
	public ResponseEntity<String> newUserByGoogle(@RequestBody User user) throws MessagingException, JsonProcessingException{
			System.out.println(user.getFullName());
			List<User> emailBasedUsers = userRepo.findByEmail(user.getEmail());
			List<User> loginBasedUsers = userRepo.findByLogin(user.getLogin());
			if(emailBasedUsers.size()>0) {
				return new ResponseEntity<String>("Email exists",HttpStatus.FORBIDDEN);
			}
			userRepo.save(user);
			Map<String,Object> response = new HashMap<>();
			response.put("userId",user.getId());
			response.put("name",user.getFullName());
			String json = new ObjectMapper().writeValueAsString(response);
			//mailService.sendEmail(MailTypes.signUp(user.getEmail()));
			return new ResponseEntity<String>(json,HttpStatus.CREATED);
		}

	@PostMapping("signup-google2/{token}")
	public ResponseEntity<String> newUserGoogle2(@PathVariable String token) throws IOException {
		// from https://developers.google.com/identity/sign-in/web/backend-auth -> don't understand how that works...
		URL url = new URL("https://www.googleapis.com/oauth2/v3/userinfo");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer "+token);
		conn.connect();
		//get the response
		int responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			return new ResponseEntity<String>("Error connecting to Google", HttpStatus.BAD_REQUEST);
		} else {
			String json = "";
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			json = sb.toString();
			//Use the objectMapper just as for the regular login -> won't work as it's not
			ObjectMapper mapper = new ObjectMapper();
			try{
				Map<String, String> map = mapper.readValue(json, Map.class);
				String email = map.get("email");
				String name = map.get("name");
				String login = "google";
				String password = "google";
				// check if email exists already
				List<User> emailBasedUsers = userRepo.findByEmail(email);
				if(emailBasedUsers.size()>0) {
					return new ResponseEntity<String>("Email exists",HttpStatus.FORBIDDEN);
				}
				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user.setFullName(name);
				user.setLogin(login);
				userRepo.save(user);
				Map<String,Object> response = new HashMap<>();
				response.put("userId",user.getId());
				response.put("name",user.getFullName());
				String jsonToReturn = new ObjectMapper().writeValueAsString(response);
				return new ResponseEntity<String>(jsonToReturn, HttpStatus.CREATED);
			} catch (IOException e) {
				return new ResponseEntity<String>("Server Error for map",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

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
			if (password.equals("google")) {
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
    			String responseJson = new ObjectMapper().writeValueAsString(response);
    			return new ResponseEntity<String>(responseJson,HttpStatus.OK);		
    		}else {
    			return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
    		}
        } catch (IOException e) {
        	return new ResponseEntity<String>("Server Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }		
	}
	
	@PostMapping("/login-google/{userEmail}")
	public ResponseEntity<String> loginByGoogle(@PathVariable String userEmail)throws JsonProcessingException{
		List<User> logedInUser = userRepo.findByEmail(userEmail);
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
