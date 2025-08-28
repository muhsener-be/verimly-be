package app.verimly.task.domain.vo.task;

import app.verimly.commons.core.domain.exception.InvalidDomainObjectException;
import app.verimly.commons.core.utils.MyStringUtils;
import app.verimly.task.data.task.TaskTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskDescriptionTest {

    private static final String BLANK_VALUE = "   ";
    private static final String NULL_VALUE = null;
    private static final String VALID_VALUE = "Valid task name";
    private static final String ANORMAL_NAME_VALUE = "  A    no r   ma l    ";
    private static final String NORMAL_NAME_VALUE = "A no r ma l";
    private static final String TOO_LONG = MyStringUtils.generateString(TaskDescription.MAX_LENGTH + 1);


    private TaskTestData DATA = TaskTestData.getInstance();


    @Test
    void of_whenValueIsNullOrBlank_thenReturnNull() {
        TaskDescription nullValue = TaskDescription.of(NULL_VALUE);
        TaskDescription blankValue = TaskDescription.of(BLANK_VALUE);

        assertNull(nullValue);
        assertNull(blankValue);
    }

    @Test
    void of_whenValuesIsTooLong_thenThrowsInvalidDomainObjectException() {

        InvalidDomainObjectException exception = assertThrows(InvalidDomainObjectException.class, () -> TaskDescription.of(TOO_LONG));

        assertEquals(TaskDescription.Errors.LENGTH, exception.getErrorMessage());
    }

    @Test
    void of_whenAnormalValue_thenReturnsNormalized() {

        TaskDescription actual = TaskDescription.of(ANORMAL_NAME_VALUE);

        assertEquals(NORMAL_NAME_VALUE, actual.getValue());
    }

    @Test
    void of_happy_path() {
        TaskDescription actual = TaskDescription.of(VALID_VALUE);

        assertEquals(VALID_VALUE, actual.getValue());
    }

    @Test
    void reconstruct_whenInvalid_doesNotCheckInvariants() {
        TaskDescription actual = TaskDescription.reconstruct(ANORMAL_NAME_VALUE);
        assertEquals(ANORMAL_NAME_VALUE, actual.getValue());

        String tooLong = DATA.tooLongDescriptionValue();
        actual = TaskDescription.reconstruct(tooLong);
        assertEquals(tooLong, actual.getValue());

    }
}