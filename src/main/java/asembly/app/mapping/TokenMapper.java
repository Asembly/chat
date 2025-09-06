package asembly.app.mapping;

import asembly.app.dto.auth.refresh.RefreshResponse;
import asembly.app.entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TokenMapper {

    RefreshResponse toTokenResponse(RefreshToken refresh);

}
