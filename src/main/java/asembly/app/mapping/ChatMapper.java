package asembly.app.mapping;

import asembly.app.dto.chat.ChatResponse;
import asembly.app.dto.chat.ChatWithUsersResponse;
import asembly.app.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChatMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    ChatResponse toChatResponse(Chat chat);
    List<ChatResponse> toChatResponseList(List<Chat> chats);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "users", source = "users")
    ChatWithUsersResponse toChatWithUsersResponse(Chat chat);

}
