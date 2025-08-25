package app.verimly.commons.core.domain.mapper;

import app.verimly.commons.core.domain.vo.Email;
import app.verimly.commons.core.domain.vo.ValueObject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoreVoMapper {

    default <T> T fromVO(ValueObject<T> valueObject) {
        return valueObject == null ? null : valueObject.getValue();
    }

    default Email toEmail(String source) {
        return Email.of(source);
    }


}
