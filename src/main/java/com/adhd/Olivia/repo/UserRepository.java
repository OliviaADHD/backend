package com.adhd.Olivia.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adhd.Olivia.models.db.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findById(int id);
	List<User> findByEmail(String email);
	List<User> findByLogin(String login);
	List<User> findByEmailAndPassword(String email,String password);
}
