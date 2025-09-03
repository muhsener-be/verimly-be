package app.verimly.commons.core.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermissionRequirementTest {


    @Test
    void name() {
        PermissionRequirement permissionRequirement = PermissionRequirement.or("A", "B").andAnd("C", "D");
        System.out.println(permissionRequirement.getExpression());
    }
}