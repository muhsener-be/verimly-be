package app.verimly.commons.core.security;

import lombok.Getter;

public class PermissionRequirement {

    public static final String AND = " and ";
    public static final String OR = " or ";
    @Getter
    private String expression;

    public PermissionRequirement(String expression) {
        this.expression = expression;
    }

    public static PermissionRequirement of(String operand) {
        return new PermissionRequirement(operand);
    }


    public static PermissionRequirement and(String op1, String op2, String... operands) {
        StringBuilder sb = new StringBuilder();
        bindInParenthesis(sb, AND, op1, op2, operands);
        return new PermissionRequirement(sb.toString());
    }

    public static PermissionRequirement or(String op1, String op2, String... operands) {
        StringBuilder sb = new StringBuilder();
        bindInParenthesis(sb, OR, op1, op2, operands);
        return new PermissionRequirement(sb.toString());
    }


    public PermissionRequirement and(String operand) {
        return new PermissionRequirement(this.expression + AND + operand);
    }

    public PermissionRequirement or(String operand) {
        return new PermissionRequirement(this.expression + OR + operand);
    }

    public PermissionRequirement andOr(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(AND);
        bindInParenthesis(sb, OR, op1, op2, ops);
        return new PermissionRequirement(sb.toString());
    }

    public PermissionRequirement andAnd(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(AND);
        bindInParenthesis(sb, AND, op1, op2, ops);
        return new PermissionRequirement(sb.toString());
    }


    public PermissionRequirement orOr(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(OR);
        bindInParenthesis(sb, OR, op1, op2, ops);
        return new PermissionRequirement(sb.toString());
    }


    public PermissionRequirement orAnd(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(OR);
        bindInParenthesis(sb, AND, op1, op2, ops);
        return new PermissionRequirement(sb.toString());
    }


    private static void bindFirstTwo(StringBuilder sb, String operator, String op1, String op2) {
        sb.append("(").append(op1).append(operator).append(op2);
    }


    private static void bindArrayOperands(StringBuilder sb, String operator, String[] operands) {
        for (String op : operands) {
            sb.append(operator).append(op);
        }

    }


    private static void bindInParenthesis(StringBuilder sb, String operator, String op1, String op2, String[] operands) {
        bindFirstTwo(sb, operator, op1, op2);
        bindArrayOperands(sb, operator, operands);
        sb.append(")");
    }

    @Override
    public String toString() {
        return expression;
    }
}
