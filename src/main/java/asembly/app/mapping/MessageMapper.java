package asembly.app.mapping;

import asembly.app.dto.message.MessageResponse;
import asembly.app.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "text", source = "text")
    @Mapping(target = "author_id", source = "author_id")
    MessageResponse toMessageResponse(Message message);
    List<MessageResponse> toMessageResponseList(List<Message> messages);

}
