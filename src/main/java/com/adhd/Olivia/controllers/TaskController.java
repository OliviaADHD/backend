package com.adhd.Olivia.controllers;

import java.util.List;
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
	
	@PostMapping
	public ResponseEntity<String> newTask(@RequestBody Tasks task){
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
		String json = new ObjectMapper().writeValueAsString(allUserTasks);
		return new ResponseEntity<String>(json,HttpStatus.CREATED);
	}
	
	@PutMapping("/{taskId}")
	public ResponseEntity<String> editTask(@PathVariable int taskId, @RequestBody String json) throws JsonMappingException, JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(json);
		String newTODO = actualObj.get("task").toPrettyString();
		Optional<Tasks> optTask = taskRepo.findById(taskId);
		if(optTask.isEmpty()) {
			return new ResponseEntity<String>("NOT FOUND",HttpStatus.NOT_FOUND);
		}
		Tasks task = optTask.get();
		task.setTodo(newTODO);
		taskRepo.save(task);
		return new ResponseEntity<String>("UPDATED",HttpStatus.OK);
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<String> editTask(@PathVariable int taskId) {
		Optional<Tasks> optTask = taskRepo.findById(taskId);
		if(optTask.isEmpty()) {
			return new ResponseEntity<String>("NOT FOUND",HttpStatus.NOT_FOUND);
		}
		Tasks task = optTask.get();
		taskRepo.delete(task);
		return new ResponseEntity<String>("DELETED",HttpStatus.OK);
	}

}
