package com.adhd.Olivia.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adhd.Olivia.models.Users;

public interface UserRepository extends CrudRepository<Users, Integer> {
	Users findById(int id);
	List<Users> findByEmail(String email);
	List<Users> findByLogin(String login);
	List<Users> findByEmailAndPassword(String email,String password);
}
