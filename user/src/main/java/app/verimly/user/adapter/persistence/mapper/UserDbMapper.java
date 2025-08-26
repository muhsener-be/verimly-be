package app.verimly.user.adapter.persistence.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapper;
import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.user.adapter.persistence.entity.UserEntity;
import app.verimly.user.domain.entity.User;
import app.verimly.user.domain.vo.Password;
import app.verimly.user.domain.vo.PersonName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CoreVoMapper.class})
public interface UserDbMapper {


    @Mapping(target = "password", source = "user.password.encrypted")
    @Mapping(target = "firstName", source = "user.name.firstName")
    @Mapping(target = "lastName", source = "user.name.lastName")
    UserEntity toJpaEntity(User user);


    default User toDomainEntity(UserEntity source) {
        return User.reconstruct(
                UserId.reconstruct(source.getId()),
                PersonName.reconstruct(source.getFirstName(), source.getLastName()),
                Email.reconstruct(source.getEmail()),
                Password.reconstruct(source.getPassword())
        );
    }


}
