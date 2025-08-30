package app.verimly.task.adapter.web.mapper;

import org.mapstruct.Mapper;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public interface ZonedTimeMapper {

    default ZonedDateTime toZonedDateTimeTimeZoneAware(Instant instant) {
        if (instant == null)
            return null;
        TimeZone userTimeZone = LocaleContextHolder.getTimeZone();
        return ZonedDateTime.ofInstant(instant, userTimeZone.toZoneId());

    }


}
