package org.arjunaoverdrive.tasktracker.web.dto.response;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String email;
}
