package app.verimly.task.application;

import app.verimly.commons.core.security.AuthenticationRequiredException;
import app.verimly.commons.core.security.Principal;
import app.verimly.task.data.SecurityTestData;
import app.verimly.task.data.TimeSessionTestData;
import app.verimly.task.data.folder.FolderTestData;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.repository.TaskDataAccessException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractUnitTest {

    protected static final SecurityTestData SECURITY_TEST_DATA = SecurityTestData.getInstance();
    protected static final TaskTestData TASK_TEST_DATA = TaskTestData.getInstance();
    protected static final FolderTestData FOLDER_TEST_DATA = FolderTestData.getInstance();
    protected static final TimeSessionTestData SESSION_TEST_DATA = TimeSessionTestData.getInstance();


    protected AuthenticationRequiredException AUTHENTICATION_REQUIRED_EXCEPTION = new AuthenticationRequiredException("Test exception.");
    protected TaskDataAccessException TASK_DATA_ACCESS_EXCEPTION = new TaskDataAccessException("Test Exception");


    protected Principal AUTHENTICATED_PRINCIPAL = SECURITY_TEST_DATA.authenticatedPrincipal();
    protected Principal ANONYMOUS_PRINCIPAL = SECURITY_TEST_DATA.anonymousPrincipal();


    protected void assertThrowsExceptions(Class<? extends Throwable> exClazz, Executable executable) {
        assertThrows(exClazz, executable);
    }

    protected void assertDoesNotThrowsException(Executable executable) {
        assertDoesNotThrow(executable);
    }

    protected void assertThrowsIllegalArgumentException(@NotNull Executable executable) {
        assertThrowsExceptions(IllegalArgumentException.class,executable);
    }
}
