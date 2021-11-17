package com.adhd.Olivia.repo;

import com.adhd.Olivia.models.db.Menstruation;
import org.springframework.data.repository.CrudRepository;

public interface MenstruationRepository extends CrudRepository<Menstruation, Integer> {
}
