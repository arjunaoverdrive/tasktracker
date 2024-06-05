package org.arjunaoverdrive.tasktracker.service.Impl;

import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.tasktracker.dao.TaskRepository;
import org.arjunaoverdrive.tasktracker.model.Task;
import org.arjunaoverdrive.tasktracker.model.TaskStatus;
import org.arjunaoverdrive.tasktracker.model.User;
import org.arjunaoverdrive.tasktracker.service.TaskService;
import org.arjunaoverdrive.tasktracker.service.UserService;
import org.arjunaoverdrive.tasktracker.util.BeanUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public Flux<Task> findAll() {
        Flux<Task> allTasksFlux = taskRepository.findAll();
        return allTasksFlux.flatMap(task -> {
            Mono<Task> taskMono = Mono.just(task);
            return zipTaskMonoWithUserMonos(taskMono);
        });
    }

    @Override
    public Mono<Task> findById(String id) {
        Mono<Task> taskMono = taskRepository.findById(id);
        return zipTaskMonoWithUserMonos(taskMono);
    }


    @Override
    public Mono<Task> save(Task task) {
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        task.setStatus(TaskStatus.TODO);
        Mono<Task> taskMono = Mono.just(task);
        taskMono = populateTaskWithUsers(taskMono);

        return zipTaskMonoWithUserMonos(taskMono).flatMap(taskRepository::save);
    }
    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }

    @Override
    public Mono<Task> updateTask(Task task) {
        Mono<Task> taskMono = findById(task.getId()).map(fromDb -> {
            BeanUtils.copyNonNullValues(task, fromDb);
            return fromDb;
        });

        return zipTaskMonoWithUserMonos(taskMono).flatMap(taskRepository::save);
    }

    private Mono<Task> populateTaskWithUsers(Mono<Task> taskMono) {
        Mono<String> authorIdMono = getCurrentUserMono();

        taskMono = Mono.zip(taskMono, authorIdMono).flatMap(
                data -> {
                    Task t1 = data.getT1();
                    t1.setAuthorId(data.getT2());
                    t1.setAssigneeId(t1.getAssigneeId() == null ? data.getT2() : t1.getAssigneeId());
                    return Mono.just(t1);
                }
        );
        return taskMono;
    }

    private Mono<String> getCurrentUserMono() {
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (UserDetails) securityContext.getAuthentication().getPrincipal())
                .flatMap(p -> userService.findByUsername(p.getUsername()))
                .map(User::getId);
    }

    private Mono<Task> zipTaskMonoWithUserMonos(Mono<Task> taskMono) {

        Mono<User> authorMono = taskMono.map(Task::getAuthorId).flatMap(userService::findById);

        Mono<User> assigneeMono = taskMono.map(Task::getAssigneeId).flatMap(userService::findById);

        Mono<Set<User>> observersMono = taskMono.map(Task::getObserverIds).flatMap(userService::findAllByIds);

        return Mono.zip(taskMono, authorMono, assigneeMono, observersMono).flatMap(data -> {
            Task t1 = data.getT1();
            t1.setAuthor(data.getT2());
            t1.setAssignee(data.getT3());
            t1.setObservers(data.getT4());
            return Mono.just(t1);
        });
    }
}
