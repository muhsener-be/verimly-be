package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.domain.vo.ValueObject;
import app.verimly.task.data.task.TaskTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskNameTest {


    private static final String BLANK_VALUE = "   ";
    private static final String NULL_VALUE = null;
    private static final String VALID_VALUE = "Valid task name";
    private static final String ANORMAL_NAME_VALUE = "  A    no r   ma l    ";
    private static final String NORMAL_NAME_VALUE = "A no r ma l";


    private TaskTestData DATA = TaskTestData.getInstance();


    @Test
    void of_whenValueIsNullOrBlank_thenReturnNull() {
        TaskName nullValue = TaskName.of(NULL_VALUE);
        TaskName blankValue = TaskName.of(BLANK_VALUE);

        assertNull(nullValue);
        assertNull(blankValue);
    }

    @Test
    void of_whenValuesIsTooLong_thenThrowsInvalidDomainObjectException() {
        String tooLong = DATA.tooLongNameValue();

        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, () -> TaskName.of(tooLong));

        assertEquals(TaskName.Errors.LENGTH, exception.getErrorMessage());
    }

    @Test
    void of_whenAnormalValue_thenReturnsNormalized() {

        TaskName taskName = TaskName.of(ANORMAL_NAME_VALUE);

        assertEquals(NORMAL_NAME_VALUE, taskName.getValue());
    }

    @Test
    void of_happy_path() {
        TaskName taskName = TaskName.of(VALID_VALUE);

        assertEquals(VALID_VALUE, taskName.getValue());
    }
}