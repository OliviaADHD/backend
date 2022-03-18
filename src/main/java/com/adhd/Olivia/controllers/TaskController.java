package com.adhd.Olivia.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adhd.Olivia.models.db.Tasks;
import com.adhd.Olivia.models.db.User;
import com.adhd.Olivia.repo.TaskRepo;
import com.adhd.Olivia.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/tasks")
public class TaskController {
	
	@Autowired
	public TaskRepo taskRepo;
	
	@Autowired
	public UserRepository userRepo;
	
	//Doesn't work take a look
	@PostMapping
	public ResponseEntity<String> newTask(@RequestBody String json) throws JsonMappingException, JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(json);
		String newTODO = actualObj.get("todo").asText();
		int userId = actualObj.get("userId").asInt();
		User usr = userRepo.findById(userId);
		Tasks task = new Tasks();
		task.setTodo(newTODO);
		task.setUser(usr);
		taskRepo.save(task);
		return new ResponseEntity<String>("OK",HttpStatus.CREATED);
	}

	@GetMapping("/all/{userId}")
	public ResponseEntity<String> getAll(@PathVariable int userId) throws JsonProcessingException{
		User usr = userRepo.findById(userId);
		Optional<List<Tasks>> optUserTasks = taskRepo.findByUser(usr);
		if(!optUserTasks.isPresent()) {
			return new ResponseEntity<String>("BAD",HttpStatus.NOT_FOUND);
		}
		List<Tasks> allUserTasks = optUserTasks.get();
		if(allUserTasks.size() == 0) {
			return new ResponseEntity<String>("BAD",HttpStatus.NOT_FOUND);
		}
		Map<String, Object> todos = new HashMap();
		List<Map<String,Object>> res = new ArrayList<>();
		for(Tasks currTask: allUserTasks) {
			todos.put("id", currTask.getId());
			todos.put("todo",currTask.getTodo());
			todos.put("completed",currTask.isCompleted());			
			res.add(todos);
			todos = new HashMap();
		}		
		String json = new ObjectMapper().writeValueAsString(res);
		return new ResponseEntity<String>(json,HttpStatus.CREATED);
	}
	
	@GetMapping("/{todoID}")
	public ResponseEntity<String> taskStatus(@PathVariable int todoID, @RequestBody String json) {
		System.out.println(json);
		Optional<Tasks> optTask = taskRepo.findById(todoID);
		if(optTask.isEmpty()) {
			return new ResponseEntity<String>("NOT FOUND",HttpStatus.NOT_FOUND);
		}
		Tasks task = optTask.get();		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(json);
		Map<String, Boolean> actualObj;
		try {
			actualObj = mapper.readValue(json, Map.class);
			boolean completed = actualObj.get("completed");
			task.setCompleted(completed);
			taskRepo.save(task);
			return new ResponseEntity<String>("COMPLETED",HttpStatus.OK);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PutMapping("/{todoID}")
	public ResponseEntity<String> editTask(@PathVariable int todoID, @RequestBody String json) throws JsonMappingException, JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(json);
		String newTODO = actualObj.get("todo").asText();
		Optional<Tasks> optTask = taskRepo.findById(todoID);
		if(optTask.isEmpty()) {
			return new ResponseEntity<String>("NOT FOUND",HttpStatus.NOT_FOUND);
		}
		Tasks task = optTask.get();
		task.setTodo(newTODO);
		taskRepo.save(task);
		return new ResponseEntity<String>("UPDATED",HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{todoID}")
	public ResponseEntity<String> deleteTask(@PathVariable int todoID) {
		Optional<Tasks> optTask = taskRepo.findById(todoID);
		if(optTask.isEmpty()) {
			return new ResponseEntity<String>("NOT FOUND",HttpStatus.NOT_FOUND);
		}
		Tasks task = optTask.get();
		taskRepo.delete(task);
		return new ResponseEntity<String>("DELETED",HttpStatus.OK);
	}

}
