package org.arjunaoverdrive.tasktracker.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class TaskSubmitRequest {
    @NotEmpty
    @Size(min = 3, max = 32)
    private String name;
    @Size(max = 1204)
    private String description;
    private String assigneeId;
    @Builder.Default
    private Set<String> observerIds = new HashSet<>();
}
