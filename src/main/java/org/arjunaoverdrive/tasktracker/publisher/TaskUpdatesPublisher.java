package org.arjunaoverdrive.tasktracker.publisher;

import org.arjunaoverdrive.tasktracker.web.dto.response.TaskResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class TaskUpdatesPublisher {

    private final Sinks.Many<TaskResponse> taskResponseUpdatesSink;

    public TaskUpdatesPublisher() {
        this.taskResponseUpdatesSink = Sinks.many().multicast().onBackpressureBuffer();;
    }

    public void publish(TaskResponse response){
        taskResponseUpdatesSink.tryEmitNext(response);
    }

    public Sinks.Many<TaskResponse> getUpdatesSink() {
        return taskResponseUpdatesSink;
    }
}
