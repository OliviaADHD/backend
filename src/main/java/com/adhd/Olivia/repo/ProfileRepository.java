package com.adhd.Olivia.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.adhd.Olivia.models.db.Profile;
import com.adhd.Olivia.models.db.User;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {
	Optional<Profile> findByUser(User user);
}
