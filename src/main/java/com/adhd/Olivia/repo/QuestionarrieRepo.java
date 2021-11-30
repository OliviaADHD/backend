package com.adhd.Olivia.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.adhd.Olivia.models.db.Questionarrie;
import com.adhd.Olivia.models.db.User;


public interface QuestionarrieRepo extends CrudRepository<Questionarrie, Integer> {
	Optional<Questionarrie> findByUser(User user);
}
