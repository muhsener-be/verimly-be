//package app.verimly.task.domain.entity;
//
//import app.verimly.task.data.task.TaskTestData;
//import app.verimly.task.domain.exception.TaskDomainException;
//import app.verimly.task.domain.vo.task.TaskId;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TaskTest {
//
//
//    private static TaskTestData DATA = TaskTestData.getInstance();
//
//    Task.TaskBuilder builder;
//
//    @BeforeEach
//    public void setup() {
//        builder = getBuilderWithBasicInformation();
//    }
//
//
//    @Test
//    void build_whenIdNotProvided_shouldInitializesWithIdAndNotStartedStatus() {
//        Task task = builder.build();
//
//        assertNotNull(task.getId());
//        assertTrue(task.isNotStarted());
//    }
//
//    @ParameterizedTest
//    @MethodSource("prepareConditionViolatingInvariant")
//    void build_whenOwnerIdOrFolderIdOrNameIsNull_thenThrows(Task.TaskBuilder builder) {
//        assertThrowsTaskDomainException(builder);
//    }
//
//
//    @Test
//    void build_whenIdProvided_thenDoesNotCheckInvariants() {
//        assertDoesNotThrow(() -> builder.id(TaskId.random()).name(null).folderId(null).ownerId(null).build());
//    }
//
//
//    static Stream<Task.TaskBuilder> prepareConditionViolatingInvariant() {
//        Task.TaskBuilder builder = getBuilderWithBasicInformation();
//        return Stream.of(
//                builder.name(null),
//                builder.folderId(null),
//                builder.ownerId(null)
//        );
//    }
//
//
//    private void assertThrowsTaskDomainException() {
//        assertThrows(TaskDomainException.class,
//                () -> builder.build());
//    }
//
//    private void assertThrowsTaskDomainException(Task.TaskBuilder builder) {
//        assertThrows(TaskDomainException.class,
//                builder::build);
//    }
//
//
//    private static Task.TaskBuilder getBuilderWithBasicInformation() {
//
//        return Task.builder()
//                .name(DATA.name())
//                .ownerId(DATA.ownerId())
//                .dueDate(DATA.dueDate())
//                .folderId(DATA.folderId())
//                .description(DATA.description())
//                .priority(DATA.priority());
//    }
//}