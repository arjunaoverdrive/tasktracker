package org.arjunaoverdrive.tasktracker.service;

import org.arjunaoverdrive.tasktracker.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserService {
    Flux<User> findAllUsers();

    Mono<Set<User>> findAllByIds(Set<String> ids);

    Mono<User> findById(String id);

    Mono<User> createUser(User user);

    Mono<User> updateUser(User user);

    Mono<Void> deleteUserById(String id);
}
