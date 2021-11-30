package com.adhd.Olivia.repo;

import java.util.Optional;

import com.adhd.Olivia.models.db.Menstruation;
import org.springframework.data.repository.CrudRepository;
import com.adhd.Olivia.models.db.User;

public interface MenstruationRepository extends CrudRepository<Menstruation, Integer> {
    Optional<Menstruation> findByUser(User user);
}
