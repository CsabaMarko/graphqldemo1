package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

public interface FieldModel<F extends Comparable<F>> {
    Class<F> getType();
    String getName();
}
