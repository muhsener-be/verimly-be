package app.verimly.task.adapter.web.mapper;

import app.verimly.commons.core.domain.mapper.CoreVoMapperImpl;
import app.verimly.commons.core.domain.mapper.ZonedTimeMapperImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TaskWebMapperImpl.class, TaskVoMapperImpl.class, CoreVoMapperImpl.class, ZonedTimeMapperImpl.class})

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


    private CreateTaskCommand mapToCommand() {
        return mapper.toCreateTaskCommand(webRequest);
    }


}