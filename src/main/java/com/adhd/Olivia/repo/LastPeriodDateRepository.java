package com.adhd.Olivia.repo;

import com.adhd.Olivia.models.db.LastPeriodDate;
import com.adhd.Olivia.models.db.Menstruation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LastPeriodDateRepository extends CrudRepository<LastPeriodDate, Integer> {
    List<LastPeriodDate> findByMenstruation(Menstruation menstruation);
}
