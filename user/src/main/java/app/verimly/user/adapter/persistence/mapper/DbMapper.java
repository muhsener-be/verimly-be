package app.verimly.user.adapter.persistence.mapper;

import app.verimly.commons.core.domain.vo.UserId;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface DbMapper {


    default UserId toUserId(UUID id) {
        return UserId.reconstruct(id);
    }

}
