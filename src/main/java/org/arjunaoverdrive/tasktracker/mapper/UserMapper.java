package org.arjunaoverdrive.tasktracker.mapper;

import org.arjunaoverdrive.tasktracker.model.User;
import org.arjunaoverdrive.tasktracker.web.dto.request.UserRequest;
import org.arjunaoverdrive.tasktracker.web.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toUserResponse(User user);

    User toUser(UserRequest request);

    default User toUser(String id, UserRequest request){
        User user = toUser(request);
        user.setId(id);
        return user;
    }

}
