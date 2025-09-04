package asembly.app.mapping;

import asembly.app.dto.message.MessageResponse;
import asembly.app.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MessageMapper {

    MessageResponse toMessageResponse(Message message);
    List<MessageResponse> toMessageResponseList(List<Message> messages);

}
