package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

public interface ValueModel<F extends Comparable<F>> {
    Class<F> getType();
    F getValue();
}
