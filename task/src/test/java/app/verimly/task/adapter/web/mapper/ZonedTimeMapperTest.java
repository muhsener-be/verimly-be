package app.verimly.task.adapter.web.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ZonedTimeMapperImpl.class)
class ZonedTimeMapperTest {

    @Autowired
    ZonedTimeMapper mapper;

    @Test
    void toZonedDateTime_whenInstantIsNull_thenReturnsNull() {
        assertNull(mapper.toZonedDateTimeTimeZoneAware(null));
    }


    @ParameterizedTest
    @MethodSource("supplyArguments")
    void toZonedDateTime_shouldBeAwareOfTimeZoneAndDST(String expected, String utcTime, String userTimeZone) {
        Instant utcInstant = Instant.parse(utcTime);
        LocaleContextHolder.setTimeZone(TimeZone.getTimeZone(userTimeZone));

        ZonedDateTime zonedTime = mapper.toZonedDateTimeTimeZoneAware(utcInstant);


        assertEquals(expected, zonedTime.toString());


    }

    static List<Arguments> supplyArguments() {
        return List.of(
                Arguments.of("2026-09-30T10:00+03:00[Europe/Istanbul]", "2026-09-30T07:00:00Z", "Europe/Istanbul"),
                Arguments.of("2026-09-30T12:00+05:00[Asia/Karachi]", "2026-09-30T07:00:00Z", "Asia/Karachi"),
                Arguments.of("2026-09-30T15:00+08:00[Asia/Shanghai]", "2026-09-30T07:00:00Z", "Asia/Shanghai"),
                Arguments.of("2026-09-30T16:00+09:00[Asia/Tokyo]", "2026-09-30T07:00:00Z", "Asia/Tokyo"),
                Arguments.of("2026-09-30T03:00-04:00[America/New_York]", "2026-09-30T07:00:00Z", "America/New_York"),
                Arguments.of("2026-09-30T00:00-07:00[America/Los_Angeles]", "2026-09-30T07:00:00Z", "America/Los_Angeles"),
                Arguments.of("2026-09-30T04:00-03:00[America/Argentina/Buenos_Aires]", "2026-09-30T07:00:00Z", "America/Argentina/Buenos_Aires"),
                // --- DST EDGE CASES 2026 ---
                // America/New_York – Spring Forward: 2026-03-08 02:00 → 03:00
                // 06:30Z = 01:30 before skip
                Arguments.of("2026-03-08T01:30-05:00[America/New_York]", "2026-03-08T06:30:00Z", "America/New_York"),
                // 07:30Z = 03:30 (02:30 does not exist)
                Arguments.of("2026-03-08T03:30-04:00[America/New_York]", "2026-03-08T07:30:00Z", "America/New_York"),

                // America/New_York – Fall Back: 2026-11-01 02:00 → 01:00
                // 05:30Z = 01:30 EDT (before rollback)
                Arguments.of("2026-11-01T01:30-04:00[America/New_York]", "2026-11-01T05:30:00Z", "America/New_York"),
                // 06:30Z = 01:30 EST (after rollback)
                Arguments.of("2026-11-01T01:30-05:00[America/New_York]", "2026-11-01T06:30:00Z", "America/New_York"),

                // Europe/Berlin – Spring Forward: 2026-03-29 02:00 → 03:00
                // 00:30Z = 01:30 CET (normal)
                Arguments.of("2026-03-29T01:30+01:00[Europe/Berlin]", "2026-03-29T00:30:00Z", "Europe/Berlin"),
                // 01:30Z = 03:30 CEST (02:30 skipped)
                Arguments.of("2026-03-29T03:30+02:00[Europe/Berlin]", "2026-03-29T01:30:00Z", "Europe/Berlin"),

                // Europe/Berlin – Fall Back: 2026-10-25 03:00 → 02:00
                // 00:30Z = 02:30 CEST (summer time)
                Arguments.of("2026-10-25T02:30+02:00[Europe/Berlin]", "2026-10-25T00:30:00Z", "Europe/Berlin"),
                // 01:30Z = 02:30 CET (standard time, second occurrence)
                Arguments.of("2026-10-25T02:30+01:00[Europe/Berlin]", "2026-10-25T01:30:00Z", "Europe/Berlin")
        );
    }
}