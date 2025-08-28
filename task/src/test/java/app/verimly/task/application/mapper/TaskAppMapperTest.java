package app.verimly.task.application.mapper;

import app.verimly.commons.core.domain.vo.UserId;
import app.verimly.task.application.usecase.command.task.create.CreateTaskCommand;
import app.verimly.task.application.usecase.command.task.create.TaskCreationResponse;
import app.verimly.task.data.task.TaskTestData;
import app.verimly.task.domain.entity.Task;
import app.verimly.task.domain.input.TaskCreationDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;


public class TaskAppMapperTest {

    TaskTestData DATA = TaskTestData.getInstance();


    TaskAppMapper mapper;

    UserId ownerId;
    CreateTaskCommand command;

    Task task;

    @BeforeEach
    public void setup() {
        mapper = Mappers.getMapper(TaskAppMapper.class);
        ownerId = UserId.random();
        command = DATA.createTaskCommand();

        task = DATA.task();
    }

    @Nested
    @DisplayName("toTaskCreationDetails() Tests")
    class ToTaskCreationDetails {
        @Test
        void toTaskCreationDetails_whenInputIsNull_thenReturnsNull() {
            TaskCreationDetails details = mapper.toTaskCreationDetails(command, ownerId);

            assertEquals(command.folderId(), details.folderId());
            assertEquals(command.name(), details.name());
            assertEquals(command.description(), details.description());
            assertEquals(ownerId, details.ownerId());
            assertEquals(command.priority(), details.priority());
            assertEquals(command.dueDate(), details.dueDate());
        }

        @Test
        void toTaskCreationsDetails_whenAllArgumentsNull_thenReturnsNull() {
            TaskCreationDetails details = mapper.toTaskCreationDetails(null, null);
            assertNull(details);
        }

        @Test
        void toTaskCreationDetails_whenCommandIsNull_thenOnlyOwnerIdBeingMapped() {
            TaskCreationDetails details = mapper.toTaskCreationDetails(null, ownerId);

            assertNull(details.name());
            assertNotNull(details.ownerId());
        }
    }

    @Nested
    @DisplayName("toTaskCreationResponse() Tests")
    class ToTaskCreationResponse {

        @Test
        void whenFolderIsNull_shouldReturnNull() {
            task = null;

            TaskCreationResponse response = mapper.toTaskCreationResponse(task);

            assertNull(response);
        }


        @Test
        void whenFolderIsValid_thenMapsToResponse() {
            TaskCreationResponse response = mapper.toTaskCreationResponse(task);

            assertNotNull(response);
            assertEquals(task.getId(), response.id());
            assertEquals(task.getName(), response.name());
            assertEquals(task.getFolderId(), response.folderId());
            assertEquals(task.getOwnerId(), response.ownerId());
            assertEquals(task.getDescription(), response.description());
            assertEquals(task.getStatus(), response.status());
            assertEquals(task.getPriority(), response.priority());
            assertEquals(task.getDueDate(), response.dueDate());
        }
    }

}
