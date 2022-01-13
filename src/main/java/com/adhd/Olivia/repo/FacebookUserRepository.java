package com.adhd.Olivia.repo;



import com.adhd.Olivia.models.db.FacebookUser;
import com.adhd.Olivia.models.db.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FacebookUserRepository extends CrudRepository<FacebookUser, Integer> {
    Optional<FacebookUser> findByUser(User user);
    List<FacebookUser> findByfbId(String fId);
}
