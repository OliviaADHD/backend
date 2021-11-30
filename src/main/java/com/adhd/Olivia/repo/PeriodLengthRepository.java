package com.adhd.Olivia.repo;

import com.adhd.Olivia.models.db.Menstruation;
import com.adhd.Olivia.models.db.PeriodLength;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodLengthRepository extends CrudRepository<PeriodLength, Integer> {
    List<PeriodLength> findByMenstruation(Menstruation menstruation);
}
