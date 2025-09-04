package asembly.app.mapping;

import asembly.app.dto.chat.ChatResponse;
import asembly.app.dto.chat.ChatWithMessagesResponse;
import asembly.app.dto.chat.ChatWithUsersResponse;
import asembly.app.entity.Chat;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    ChatResponse toChatResponse(Chat chat);
    List<ChatResponse> toChatResponseList(List<Chat> chats);

    ChatWithUsersResponse toChatWithUsersResponse(Chat chat);

    ChatWithMessagesResponse toChatWithMessagesResponse(Chat chat);
}
