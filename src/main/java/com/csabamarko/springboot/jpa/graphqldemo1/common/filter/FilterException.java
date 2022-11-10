package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

public class FilterException extends IllegalArgumentException {

    public FilterException(String s) {
        super(s);
    }

    public FilterException(String message, Throwable cause) {
        super(message, cause);
    }


}
