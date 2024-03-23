package org.arjunaoverdrive.tasktracker.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.tasktracker.mapper.TaskMapper;
import org.arjunaoverdrive.tasktracker.model.Task;
import org.arjunaoverdrive.tasktracker.publisher.TaskUpdatesPublisher;
import org.arjunaoverdrive.tasktracker.service.TaskService;
import org.arjunaoverdrive.tasktracker.web.dto.request.AddObserversRequest;
import org.arjunaoverdrive.tasktracker.web.dto.request.TaskSubmitRequest;
import org.arjunaoverdrive.tasktracker.web.dto.request.TaskUpsertRequest;
import org.arjunaoverdrive.tasktracker.web.dto.response.TaskResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskUpdatesPublisher publisher;

    @GetMapping
    public Flux<TaskResponse> getAllTasks() {
        Flux<Task> allTasksFlux = taskService.findAll();
        return allTasksFlux.map(taskMapper::toTaskResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskResponse>> getTaskById(@PathVariable String id) {
        return taskService.findById(id)
                .map(taskMapper::toTaskResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<TaskResponse>> createTask(@RequestBody TaskSubmitRequest request) {
        Task task = taskMapper.toTask(request);
        return taskService.save(task)
                .map(taskMapper::toTaskResponse)
                .doOnSuccess(publisher::publish)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<TaskResponse>> updateTaskById(@PathVariable String id,
                                                             @Validated @RequestBody TaskUpsertRequest request) {
        Task task = taskMapper.toTask(id, request);
        return taskService.updateTask(task)
                .map(taskMapper::toTaskResponse)
                .doOnSuccess(publisher::publish)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/observers/add")
    public Mono<ResponseEntity<TaskResponse>> addObserversToTask(@PathVariable String id,
                                                                 @Validated @RequestBody AddObserversRequest request){
        Task task = taskMapper.toTask(id, request);
        return taskService.updateTask(task)
                .map(taskMapper::toTaskResponse)
                .doOnSuccess(publisher::publish)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return taskService.deleteById(id);
    }


    @GetMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<TaskResponse>> getTaskUpdates(){
        return publisher.getUpdatesSink()
                .asFlux()
                .map(task -> ServerSentEvent.builder(task).build());
    }
}
