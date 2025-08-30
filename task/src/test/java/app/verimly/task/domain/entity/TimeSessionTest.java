package app.verimly.task.domain.entity;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.data.TimeSessionTestData;
import app.verimly.task.domain.exception.TimeSessionDomainException;
import app.verimly.task.domain.vo.session.SessionName;
import app.verimly.task.domain.vo.task.TaskId;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TimeSessionTest {

    private static final TimeSessionTestData SESSION_DATA = TimeSessionTestData.getInstance();

    static TaskId taskId;
    static SessionName name;
    static UserId ownerId;


    @BeforeEach
    public void setup() {
        taskId = TaskId.random();
        name = SESSION_DATA.name();
        ownerId = UserId.random();
    }


    public static Stream<Arguments> supplyNullArgumentToStartTask() {
        return Stream.of(
                Arguments.arguments(null, null, null),
                Arguments.arguments(taskId, null, null),
                Arguments.arguments(null, name, null),
                Arguments.arguments(null, null, ownerId)

        );
    }

    @ParameterizedTest
    @MethodSource("supplyNullArgumentToStartTask")
    @DisplayName("Null Value for one of them: {taskId, name, ownerId} ")
    void startForTask_whenOneOfTheArgumentIsNull_thenTimeSessionDomainException(TaskId argumentTaskId, SessionName argumentName, UserId argOwnerId) {
        taskId = argumentTaskId;
        name = argumentName;
        ownerId = argOwnerId;

        assertThrows(TimeSessionDomainException.class, getStartExecutable());
    }

    @Test
    @DisplayName("HAPPY PATH")
    void startForTask_whenValidTaskId_thenStartsSession() {

        TimeSession session = getNewlyStartedSession();

        assertEquals(name, session.getName());
        assertNotNull(session.getId());
        assertNotNull(session.getTaskId());
        assertNotNull(session.getStartedAt());
        assertTrue(session.isRunning());
        assertEquals(Duration.ZERO, session.getTotalPause());
        assertNull(session.getFinishedAt());
        assertNull(session.getPausedAt());

    }


    @Test
    void pause_whenRunning_thenPausesSession() {
        TimeSession session = getNewlyStartedSession();
        assertNull(session.getPausedAt());

        session.pause();

        assertNotNull(session.getPausedAt());
        assertTrue(session.isPaused());

    }


    @Test
    void pause_whenPaused_thenThrows() {
        TimeSession session = getNewlyStartedSession();
        session.pause();

        Executable runnable = session::pause;

        TimeSessionDomainException exception = assertThrows(TimeSessionDomainException.class, runnable);
        System.out.println(exception.getErrorMessage());

    }

    @Test
    void pause_whenFinished_thenThrows() {
        TimeSession session = getNewlyStartedSession();
        session.finish();

        Executable runnable = session::pause;

        TimeSessionDomainException exception = assertThrows(TimeSessionDomainException.class, runnable);
        System.out.println(exception.getErrorMessage());

    }

    @Test
    void resume_whenPause_thenCalculatesTotalPause() throws InterruptedException {
        TimeSession session = getNewlyStartedSession();
        session.pause();

        Thread.sleep(10);
        session.resume();

        assertTrue(session.isRunning());
        assertNotEquals(Duration.ZERO, session.getTotalPause());
        System.out.println(session.getTotalPause());
    }

    @Test
    void resume_whenFinished_thenCalculatesPauseTimeAndAddsToTotal() {
        TimeSession session = getNewlyStartedSession();
        session.finish();
        assertTrue(session.isFinished());
        assertEquals(Duration.ZERO, session.getTotalPause());


        session.resume();

        assertTrue(session.isRunning());
        assertNotEquals(Duration.ZERO, session.getTotalPause());
        System.out.println(session.getTotalPause());
    }

    @Test
    void resume_whenRunning_thenThrows() {
        TimeSession session = getNewlyStartedSession();
        assertTrue(session.isRunning());

        TimeSessionDomainException exception = assertThrows(TimeSessionDomainException.class, session::resume, "A running session cannot be resumed.");

        System.out.println(exception.getErrorMessage());
    }

    @Test
    void finish_whenRunning_thenDoesNotAddLasBreakTime() throws InterruptedException {
        TimeSession session = getNewlyStartedSession();
        assertEquals(Duration.ZERO, session.getTotalPause());
        assertNull(session.getFinishedAt());

        Thread.sleep(10);

        session.finish();

        assertTrue(session.isFinished());
        assertNotNull(session.getFinishedAt());
        assertEquals(Duration.ZERO, session.getTotalPause());
        System.out.println(session.getTotalPause());
    }


    @Test
    void finish_whenPaused_thenAddsLastBreakTimeToTotalPause() throws InterruptedException {
        TimeSession session = getNewlyStartedSession();
        session.pause();

        Thread.sleep(10);

        session.finish();

        assertTrue(session.isFinished());
        assertNotNull(session.getFinishedAt());
        assertNotEquals(Duration.ZERO, session.getTotalPause());
        System.out.println(session.getTotalPause());
    }

    @Test
    void finish_whenAlreadyFinished_thenThrows() {
        TimeSession session = getNewlyStartedSession();
        session.finish();


        TimeSessionDomainException ex = assertThrows(TimeSessionDomainException.class, session::finish);
        System.out.println(ex.getErrorMessage());
    }


    private static @NotNull TimeSession getNewlyStartedSession() {
        return TimeSession.startForTask(taskId, name, ownerId);
    }


    private static @NotNull Executable getStartExecutable() {
        return TimeSessionTest::getNewlyStartedSession;
    }
}