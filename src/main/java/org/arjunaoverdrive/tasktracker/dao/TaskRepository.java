package org.arjunaoverdrive.tasktracker.dao;

import org.arjunaoverdrive.tasktracker.model.Task;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository extends ReactiveMongoRepository<Task, String> {
}
