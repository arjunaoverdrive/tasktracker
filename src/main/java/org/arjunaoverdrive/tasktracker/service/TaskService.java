package org.arjunaoverdrive.tasktracker.service;

import org.arjunaoverdrive.tasktracker.model.Task;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskService {
    Flux<Task> findAll();

    Mono<Task> findById(String id);

    Mono<Task> save( Task task);

    Mono<Void> deleteById(String id);

    Mono<Task> updateTask(Task task);
}
