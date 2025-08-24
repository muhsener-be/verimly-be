package app.verimly.commons.core.domain.vo;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserIdTest {

    private static final UUID A_UUID = UUID.randomUUID();

    @Test
    public void of_whenValueIsNull_thenReturnNull() {
        // Arrange
        UserId expected = null;

        // Act
        UserId actual = UserId.of(null);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void of_whenValueIsNotNull_success() {
        UUID expectedUUID = A_UUID;

        UserId actual = UserId.of(expectedUUID);

        assertEquals(expectedUUID, actual.getValue());

    }
}
