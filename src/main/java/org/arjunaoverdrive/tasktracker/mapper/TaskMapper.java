package org.arjunaoverdrive.tasktracker.mapper;

import org.arjunaoverdrive.tasktracker.model.Task;
import org.arjunaoverdrive.tasktracker.web.dto.request.AddObserversRequest;
import org.arjunaoverdrive.tasktracker.web.dto.request.TaskSubmitRequest;
import org.arjunaoverdrive.tasktracker.web.dto.request.TaskUpsertRequest;
import org.arjunaoverdrive.tasktracker.web.dto.response.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

     TaskResponse toTaskResponse(Task task);

     Task toTask(TaskSubmitRequest request);
     Task toTask(TaskUpsertRequest request);
     default Task toTask(String id, TaskUpsertRequest request){
          Task task = toTask(request);
          task.setId(id);
          return task;
     }

     Task toTask(AddObserversRequest request);
     default Task toTask(String id, AddObserversRequest request){
          Task task = toTask(request);
          task.setId(id);
          return task;
     }
}
