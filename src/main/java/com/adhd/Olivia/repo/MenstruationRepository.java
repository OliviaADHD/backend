package com.adhd.Olivia.repo;

import com.adhd.Olivia.models.db.Menstruation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MenstruationRepository extends CrudRepository<Menstruation, Integer> {
    Menstruation findById(int id);
    List<Menstruation> findByLogin(String login);
}
