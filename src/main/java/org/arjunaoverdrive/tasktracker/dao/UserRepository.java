package org.arjunaoverdrive.tasktracker.dao;

import org.arjunaoverdrive.tasktracker.model.User;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByUsername(String username);
}
