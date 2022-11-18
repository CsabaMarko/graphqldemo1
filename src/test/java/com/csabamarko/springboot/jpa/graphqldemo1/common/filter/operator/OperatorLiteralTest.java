package com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator;

import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorLiteralTest {

    @Test
    void fromString_null() {
        final String str = null;
        assertThrows(FilterException.class, () -> OperatorLiteral.fromString(str));
    }

    @Test
    void fromString_nonExisting() {
        var str = "thisOperatorShouldNotExist123";
        assertThrows(FilterException.class, () -> OperatorLiteral.fromString(str));
    }

    @Test
    void fromString_normal() {
        var operator = OperatorLiteral.EQUALS;
        var str = operator.getText();
        assertEquals(operator, OperatorLiteral.fromString(str));
    }

    @Test
    void fromString_normalUpperCase() {
        var operator = OperatorLiteral.EQUALS;
        var str = operator.getText().toUpperCase();
        assertEquals(operator, OperatorLiteral.fromString(str));
    }

    @Test
    void fromString_normalLowerCase() {
        var operator = OperatorLiteral.EQUALS;
        var str = operator.getText().toLowerCase();
        assertEquals(operator, OperatorLiteral.fromString(str));
    }

}