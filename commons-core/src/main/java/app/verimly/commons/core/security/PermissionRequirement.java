package app.verimly.commons.core.security;

import lombok.Getter;

public class PermissionRequirement {

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
        sb.append("(");
        sb.append(op1).append(" and ").append(op2);
        for (String operand : operands) {
            sb.append(" and ").append(operand);
        }
        sb.append(")");
        return new PermissionRequirement(sb.toString());
    }

    public static PermissionRequirement or(String op1, String op2, String... operands) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(op1).append(" or ").append(op2);
        for (String operand : operands) {
            sb.append(" or ").append(operand);
        }
        sb.append(")");
        return new PermissionRequirement(sb.toString());
    }


    public PermissionRequirement and(String operand) {
        return new PermissionRequirement(this.expression + " and " + operand);
    }

    public PermissionRequirement or(String operand) {
        return new PermissionRequirement(this.expression + " or " + operand);
    }

    public PermissionRequirement andOr(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(" and ");
        sb.append("(").append(op1).append(" or ").append(op2);
        for (String op : ops) {
            sb.append(" or ").append(op);
        }

        sb.append(")");
        return new PermissionRequirement(sb.toString());
    }

    public PermissionRequirement andAnd(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(" and ");
        sb.append("(").append(op1).append(" and ").append(op2);
        for (String op : ops) {
            sb.append(" and ").append(op);
        }

        sb.append(")");
        return new PermissionRequirement(sb.toString());
    }

    public PermissionRequirement orOr(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(" or ");
        sb.append("(").append(op1).append(" or ").append(op2);
        for (String op : ops) {
            sb.append(" or ").append(op);
        }

        sb.append(")");
        return new PermissionRequirement(sb.toString());
    }

    public PermissionRequirement orAnd(String op1, String op2, String... ops) {
        StringBuilder sb = new StringBuilder(this.expression);
        sb.append(" or ");
        sb.append("(").append(op1).append(" and ").append(op2);
        for (String op : ops) {
            sb.append(" and ").append(op);
        }

        sb.append(")");
        return new PermissionRequirement(sb.toString());
    }


}
