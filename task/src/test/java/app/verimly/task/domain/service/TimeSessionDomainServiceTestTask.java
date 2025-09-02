package app.verimly.task.domain.service;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.TaskAbstractUnitTest;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.entity.TimeSession;
import app.verimly.task.domain.exception.TimeSessionDomainException;
import app.verimly.task.domain.input.SessionCreationDetails;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = TimeSessionDomainService.class)
class TimeSessionDomainServiceTestTask extends TaskAbstractUnitTest {

    static UserId ownerId;
    static Task task;
    static SessionCreationDetails sessionCreationDetails;


    @Autowired
    TimeSessionDomainService service;

    public static Stream<Arguments> providerNullArgumentsForStartSessionForTask() {
        return Stream.of(
                Arguments.arguments(null, null),
                Arguments.arguments(task, null),
                Arguments.arguments(null, sessionCreationDetails)
        );
    }

    @BeforeEach
    void setup() {
        ownerId = UserId.random();
        task = TASK_TEST_DATA.taskWithOwnerId(ownerId);
        sessionCreationDetails = SESSION_TEST_DATA.sessionCreationDetailsWithOwnerId(ownerId);
    }

    @ParameterizedTest
    @MethodSource("providerNullArgumentsForStartSessionForTask")
    void createSessionForTask_whenTaskIsNull_thenThrowsIllegalArgumentException(Task argTask, SessionCreationDetails argDetails) {
        task = argTask;
        sessionCreationDetails = argDetails;

        assertThrowsIllegalArgumentException(getExecutable());
    }

    @Test
    void createSessionForTask_whenOwnersDoesNotMatch_thenTimeSessionDomainException() {
        UserId randomSessionOwnerId = UserId.random();
        sessionCreationDetails = SESSION_TEST_DATA.sessionCreationDetailsWithOwnerId(randomSessionOwnerId);

        assertThrowsExceptions(TimeSessionDomainException.class, getExecutable());
    }

    @Test
    void createSessionForTask_whenArgumentValid_thenReturnsSession() {

        TimeSession session = service.startSessionForTask(task, sessionCreationDetails);

        assertNotNull(session);
        assertEquals(task.getId(), session.getTaskId());
        assertEquals(task.getOwnerId(), session.getOwnerId());
        assertEquals(sessionCreationDetails.name() , session.getName());

    }

    private void assertNotNull(TimeSession session) {
    }

    private @NotNull Executable getExecutable() {
        return () -> service.startSessionForTask(task, sessionCreationDetails);
    }
}