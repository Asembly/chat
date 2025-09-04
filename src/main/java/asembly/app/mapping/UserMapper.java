package asembly.app.mapping;

import asembly.app.dto.user.UserResponse;
import asembly.app.dto.user.UserWithChatsResponse;
import asembly.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> users);

    UserWithChatsResponse toUserWithChats(User user);

}
