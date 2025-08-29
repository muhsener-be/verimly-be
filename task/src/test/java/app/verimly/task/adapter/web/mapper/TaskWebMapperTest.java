package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.task.adapter.web.dto.request.CreateTaskWebRequest;
import app.verimly.task.adapter.web.dto.response.TaskCreationWebResponse;
import app.verimly.task.adapter.web.dto.response.TaskSummaryWebResponse;
import app.verimly.task.application.dto.TaskSummaryData;
import app.verimly.task.application.mapper.TaskVoMapperImpl;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.utils.FixtureUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TaskWebMapperImpl.class, TaskVoMapperImpl.class, CoreVoMapperImpl.class})
class TaskWebMapperTest {
    TaskTestData DATA = TaskTestData.getInstance();

    CreateTaskWebRequest webRequest;

    TaskCreationResponse response;


    private static final int SUMMARY_DATA_COUNT = 10;
    List<TaskSummaryData> summaryData;


    @Autowired
    TaskWebMapper mapper;

    @BeforeEach
    public void setup() {
        webRequest = FixtureUtils.createTaskWebRequest();
        response = DATA.taskCreationResponse();
        summaryData = DATA.summaryDatas(SUMMARY_DATA_COUNT);

    }


    @Test
    void should_setup_is_ok() {
        assertNotNull(webRequest);
        assertNotNull(mapper);

    }

    @Test
    void toCreateTaskCommand_nullInput_thenReturnNull() {
        webRequest = null;

        CreateTaskCommand command = mapToCommand();

        assertNull(command);
    }


    @Test
    void toCreateTaskCommand_validRequest_thenMapsToCommand() {
        CreateTaskCommand command = mapToCommand();


        assertEquals(webRequest.getFolderId(), command.folderId().getValue());
        assertEquals(webRequest.getDescription(), command.description().getValue());
        assertEquals(webRequest.getName(), command.name().getValue());
        assertEquals(webRequest.getPriority(), command.priority().name());
        assertEquals(webRequest.getDueDate().toInstant(), command.dueDate().getValue());
    }


    @Test
    void toTaskCreationWebResponse_validResponse_thenMapsSuccessfullyByAdjustingTimeZone() {


        TaskCreationWebResponse webResponse = mapper.toTaskCreationWebResponse(response);

        assertEquals(response.id().getValue(), webResponse.getId());
        assertEquals(response.name().getValue(), webResponse.getName());
        assertEquals(response.status().name(), webResponse.getStatus());
        assertEquals(response.priority().name(), webResponse.getPriority());
        assertEquals(response.description().getValue(), webResponse.getDescription());
        assertEquals(response.folderId().getValue(), webResponse.getFolderId());
        assertEquals(response.ownerId().getValue(), webResponse.getOwnerId());
        assertNotNull(webResponse.getDueDate());
    }


    @ParameterizedTest
    @MethodSource("supplyArguments")
    void toZonedDateTime_shouldBeAwareOfTimeZoneAndDST(String expected, String utcTime, String userTimeZone) {
        Instant utcInstant = Instant.parse(utcTime);
        LocaleContextHolder.setTimeZone(TimeZone.getTimeZone(userTimeZone));

        ZonedDateTime zonedTime = mapper.toZonedDateTimeTimeZoneAware(utcInstant);


        assertEquals(expected, zonedTime.toString());


    }


    @Test
    void toZonedDateTime_whenInstantIsNull_thenReturnsNull() {
        assertNull(mapper.toZonedDateTimeTimeZoneAware(null));
    }

    @Test
    void toTaskSummaryWebResponse_shouldMapSuccessfully() {

        List<TaskSummaryWebResponse> webResponses = mapper.toTaskSummaryWebResponse(summaryData);

        assertEquals(summaryData.size(), webResponses.size());
        assertSummaryDataListAndSummaryWebResponseListsAreEqual(webResponses, summaryData);
    }

    private void assertSummaryDataListAndSummaryWebResponseListsAreEqual(List<TaskSummaryWebResponse> webResponses, List<TaskSummaryData> summaryData) {
        for (int i = 0; i < SUMMARY_DATA_COUNT; i++) {
            TaskSummaryData expected = summaryData.get(i);
            TaskSummaryWebResponse actual = webResponses.get(i);
            assertEquals(expected.id(), actual.getId());
            assertEquals(expected.name(), actual.getName());
            assertEquals(expected.folderId(), actual.getFolderId());
            assertEquals(expected.ownerId(), actual.getOwnerId());
            assertEquals(expected.status(), actual.getStatus().name());
            assertEquals(expected.priority(), actual.getPriority());
            assertEquals(expected.description(), actual.getDescription());
            assertTrue(areInstantAndZonedDateTimeEqual(actual.getDueDate(), expected.dueDate()));
            assertTrue(areInstantAndZonedDateTimeEqual(actual.getCreatedAt(), expected.createdAt()));
            assertTrue(areInstantAndZonedDateTimeEqual(actual.getUpdatedAt(), expected.updatedAt()));


        }
    }

    @Test
    void toTaskSummaryWebResponse_whenInputNull_thenReturnsNull() {
        List<TaskSummaryData> nullList = null;
        assertNull(mapper.toTaskSummaryWebResponse(nullList));
    }

    @Test
    void toTaskSummaryWebResponse_whenListIsEmpty_thenReturnsEmpty() {
        List<TaskSummaryData> emptyList = List.of();
        assertTrue(mapper.toTaskSummaryWebResponse(emptyList).isEmpty());
    }

    @Test
    void toTaskSummaryWebResponse_thenListContainsNullElement_thenSkipsIt() {
        ArrayList<TaskSummaryData> mutableList = new ArrayList<>(summaryData);
        mutableList.add(null);
        assertEquals(summaryData.size() + 1, mutableList.size());

        List<TaskSummaryWebResponse> webResponses = mapper.toTaskSummaryWebResponse(mutableList);

        assertEquals(summaryData.size(), webResponses.size());
        assertSummaryDataListAndSummaryWebResponseListsAreEqual(webResponses, summaryData);

    }

    boolean areInstantAndZonedDateTimeEqual(ZonedDateTime zonedDateTime, Instant instant) {
        return Objects.equals(
                zonedDateTime != null ? zonedDateTime.toInstant() : null,
                instant
        );

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

    private CreateTaskCommand mapToCommand() {
        return mapper.toCreateTaskCommand(webRequest);
    }


}