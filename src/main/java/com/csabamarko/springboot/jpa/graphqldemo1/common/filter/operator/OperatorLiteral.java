package com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator;

import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterException;

public enum OperatorLiteral {

    EQUALS("equals"),
    NOT_EQUALS("notEquals"),
    LESS_THAN("lessThan"),
    GREATER_THAN("greaterThan");

    private final String text;

    OperatorLiteral(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static OperatorLiteral fromString(String str) {
        if (str == null) {
            throw new FilterException(String.format("The null value does not match with any of the possible %s values",
                    OperatorLiteral.class.getSimpleName()));
        }
        for (OperatorLiteral operatorLiteral : OperatorLiteral.values()) {
            if (operatorLiteral.getText().equalsIgnoreCase(str)) {
                return operatorLiteral;
            }
        }
        throw new FilterException(String.format("The value '%s' does not match with any of the possible %s values",
                str, OperatorLiteral.class.getSimpleName()));
    }

}
