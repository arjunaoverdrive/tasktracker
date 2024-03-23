package org.arjunaoverdrive.tasktracker.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class AddObserversRequest {

    @NotEmpty
    private List<String> observerIds;
}
