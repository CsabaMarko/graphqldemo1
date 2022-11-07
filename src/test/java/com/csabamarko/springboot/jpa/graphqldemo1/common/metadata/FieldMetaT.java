package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import lombok.Getter;

@Getter
public class FieldMetaT<F extends Comparable<F>> {
    final Class<F> type;
    final String name;
    final boolean id;

    public FieldMetaT(Class<F> type, String name, boolean id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public FieldMetaT(Class<F> type, String name) {
        this(type, name, false);
    }

}
