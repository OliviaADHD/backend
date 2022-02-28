package com.adhd.Olivia.controllers.sign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.db.FacebookUser;
import com.adhd.Olivia.models.db.Profile;
import com.adhd.Olivia.models.db.Questionarrie;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.FacebookUserRepository;
import com.adhd.Olivia.repo.ProfileRepository;
import com.adhd.Olivia.repo.QuestionarrieRepo;
import com.adhd.Olivia.repo.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/user")
public class FacebookController {

	@Autowired
	public UserRepository userRepo;
	
	@Autowired
	public QuestionarrieRepo questionRepo;
	
	@Autowired
	public FacebookUserRepository fbRepo;
	
	@Autowired
	public ProfileRepository profileRepo;
	
	@PostMapping("signup-facebook/{token}")
	public ResponseEntity<String> newUserFacebook(@PathVariable String token) throws IOException {
		URL url = new URL("https://graph.facebook.com/v12.0/me?fields=id%2Cname%2Cemail&access_token="+token);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		int responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			return new ResponseEntity<String>("Error connecting to Facebook", HttpStatus.BAD_REQUEST);
		} else {
			String json = "";
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream()), "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String output;
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			json = sb.toString();
			ObjectMapper mapper = new ObjectMapper();
			try{
				Map<String, String> map = mapper.readValue(json, Map.class);
				String email = map.get("email");
				String name = map.get("name");
				String fb_id = map.get("id");
				String login = "facebook";
				String password = "facebook";
				// check if email exists already
				List<User> emailBasedUsers = userRepo.findByEmail(email);
				if(emailBasedUsers.size()>0) {
					return new ResponseEntity<String>("Email exists",HttpStatus.FORBIDDEN);
				}
				List<FacebookUser> facebookUsers = fbRepo.findByfbId(fb_id);
				if (facebookUsers.size()>0) {
					return new ResponseEntity<String>("fb exists",HttpStatus.FORBIDDEN);
				}
				User user = new User();
				user.setEmail(email);
				user.setPassword(password);
				user.setFullName(name);
				user.setLogin(login);
				userRepo.save(user);

				FacebookUser fbUser = new FacebookUser();
				fbUser.setUser(user);
				fbUser.setFbId(fb_id);
				fbRepo.save(fbUser);

				Profile newProfile = new Profile();
				newProfile.setUser(user);
				profileRepo.save(newProfile);
				
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


	@PostMapping("login-facebook/{token}")
	public ResponseEntity<String> loginByFacebook(@PathVariable String token) throws IOException {
		URL url = new URL("https://graph.facebook.com/v12.0/me?fields=id%2Cname%2Cemail&access_token="+token);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		//get the response
		int responseCode = conn.getResponseCode();
		if (responseCode != 200) {
			return new ResponseEntity<String>("Error connecting to Facebook", HttpStatus.BAD_REQUEST);
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
				String fb_id = map.get("id");
				// check if email exists already
				List<User> emailBasedUsers = userRepo.findByEmail(email);
				List<FacebookUser> facebookUsers = fbRepo.findByfbId(fb_id);
				if(facebookUsers.size()==1) {
					User user = facebookUsers.get(0).getUser();
					if (emailBasedUsers.size()==1) {
						if (facebookUsers.get(0).getUser() != emailBasedUsers.get(0)) {
							user.setEmail(email);
							userRepo.save(user);
						}
					} else {
						user.setEmail(email);
						userRepo.save(user);
					}
					Map<String,Object> response = new HashMap<>();
					response.put("userId",user.getId());
					response.put("name",user.getFullName());
					Optional<Questionarrie> firstTime = questionRepo.findByUser(user);
					if(firstTime.isPresent()) {
						response.put("firstTime",false);
					}else {
						response.put("firstTime",true);
					}
					
	    			Optional<Profile> userProfile = profileRepo.findByUser(user);
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
				} else {
					return new ResponseEntity<String>("Not Found",HttpStatus.NOT_FOUND);
				}

			} catch (IOException e) {
				return new ResponseEntity<String>("Server Error for map",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

	}
	


	

}
