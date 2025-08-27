package app.verimly.commons.core.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnonymousPrincipalTest {


    @Test
    void construct_thenConstructsWithNullUserIdAndNullEmail() {
        AnonymousPrincipal actual = new AnonymousPrincipal();

        assertNull(actual.getId());
        assertNull(actual.getEmail());

    }
}