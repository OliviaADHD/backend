package com.adhd.Olivia.repo;

import com.adhd.Olivia.models.db.MainPageTutorial;
import com.adhd.Olivia.models.db.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MainPageTutorialRepository extends CrudRepository<MainPageTutorial, Integer> {
    Optional<MainPageTutorial> findByUser(User user);
}
