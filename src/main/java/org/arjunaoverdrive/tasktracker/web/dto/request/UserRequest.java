package org.arjunaoverdrive.tasktracker.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.arjunaoverdrive.tasktracker.model.RoleType;

import java.util.List;

@Data
public class UserRequest {
    @NotBlank
    private String username;
    @Email
    private String email;

    @NotEmpty
    private List<RoleType> roles;

    private String password;
}
