package org.arjunaoverdrive.tasktracker.web.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.arjunaoverdrive.tasktracker.validation.annotations.AtLeastOneFilled;
import org.arjunaoverdrive.tasktracker.validation.annotations.ValidStatus;

@AtLeastOneFilled
@Data
public class TaskUpsertRequest {
    @Size(min = 3, max = 32)
    private String name;
    @Size(max = 1204)
    private String description;
    @ValidStatus
    private String status;
    private String authorId;
    private String assigneeId;
}
