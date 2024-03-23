package org.arjunaoverdrive.tasktracker.dao;

import org.arjunaoverdrive.tasktracker.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
