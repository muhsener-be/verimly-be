package app.verimly.commons.core.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PermissionRequirementTest {


    public static final String OP_1 = "A";
    public static final String OP_2 = "B";
    public static final String OP_3 = "C";
    public static final String OP_4 = "D";

    @Test
    void constructAnd_givesInParenthesis() {
        PermissionRequirement req = PermissionRequirement.and(OP_1, OP_2);
        assertEquals("(%s and %s)".formatted(OP_1, OP_2), req.toString());

    }

    @Test
    void constructOr_giveInParenthesis() {
        PermissionRequirement req = PermissionRequirement.or(OP_1, OP_2);
        assertEquals("(%s or %s)".formatted(OP_1, OP_2), req.getExpression());
    }

    @Test
    void bindAnd_thenBindsOperandWithANd() {
        PermissionRequirement expr = PermissionRequirement.or(OP_1, OP_2);

        PermissionRequirement actual = expr.and(OP_3);

        assertEquals("(%s or %s) and %s".formatted(OP_1, OP_2, OP_3), actual.getExpression());

        System.out.println(actual);
    }


    @Test
    void bindOr_thenBindsOperandWithOR() {
        PermissionRequirement expr = PermissionRequirement.or(OP_1, OP_2);

        PermissionRequirement actual = expr.or(OP_3);

        assertEquals("(%s or %s) or %s".formatted(OP_1, OP_2, OP_3), actual.getExpression());

        System.out.println(actual);
    }

    @Test
    void bindAnd2_thenBindsOperandWithANd() {
        PermissionRequirement expr = PermissionRequirement.and(OP_1, OP_2);

        PermissionRequirement actual = expr.and(OP_3);

        assertEquals("(%s and %s) and %s".formatted(OP_1, OP_2, OP_3), actual.getExpression());

        System.out.println(actual);
    }


    @Test
    void bindOr2_thenBindsOperandWithOR() {
        PermissionRequirement expr = PermissionRequirement.and(OP_1, OP_2);

        PermissionRequirement actual = expr.or(OP_3);

        assertEquals("(%s and %s) or %s".formatted(OP_1, OP_2, OP_3), actual.getExpression());

        System.out.println(actual);
    }


    @Test
    void andAnd_shouldTakesInANDParenthesisAndBindWithAND() {
        PermissionRequirement expr = PermissionRequirement.of(OP_1).andAnd(OP_2, OP_3);

        assertEquals("%s and (%s and %s)".formatted(OP_1, OP_2, OP_3), expr.getExpression());
        System.out.println(expr);


    }

    @Test
    void orOr_shouldTakesInORParenthesisAndBindsWithOR() {
        PermissionRequirement expr = PermissionRequirement.of(OP_1).orOr(OP_2, OP_3,OP_4);

        assertEquals("%s or (%s or %s or %s)".formatted(OP_1, OP_2, OP_3,OP_4), expr.getExpression());
        System.out.println(expr);
    }

    @Test
    void andOr_shouldTakesInORParenthesisAndBindsWithAND() {
        PermissionRequirement expr = PermissionRequirement.of(OP_1).andOr(OP_2, OP_3, OP_4);

        assertEquals("%s and (%s or %s or %s)".formatted(OP_1, OP_2, OP_3, OP_4), expr.getExpression());
        System.out.println(expr);
    }





    @Test
    void orAnd_shouldTakesInANDParenthesisAndBindsWithOR() {
        PermissionRequirement expr = PermissionRequirement.of(OP_1).orAnd(OP_2, OP_3);

        assertEquals("%s or (%s and %s)".formatted(OP_1, OP_2, OP_3), expr.getExpression());
        System.out.println(expr);
    }


}