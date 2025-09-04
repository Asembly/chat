package asembly.app.mapping;

import asembly.app.dto.user.UserResponse;
import asembly.app.dto.user.UserWithChatsResponse;
import asembly.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> users);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "chats", source = "chats")
    UserWithChatsResponse toUserWithChats(User user);

}
