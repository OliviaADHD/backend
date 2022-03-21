package com.adhd.Olivia.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


import com.adhd.Olivia.models.db.Tasks;
import com.adhd.Olivia.models.db.User;

public interface TaskRepo extends CrudRepository<Tasks, Integer> {
	Optional<List<Tasks>> findByUser(User user);
	Optional<List<Tasks>> findByUserAndCompleted(User user, boolean completed);
}
