package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import lombok.Getter;

@Getter
public class FieldMeta {
    final Class<? extends Comparable> type;
    final String name;
    final boolean id;

    public FieldMeta(Class<? extends Comparable> type, String name, boolean id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public FieldMeta(Class<? extends Comparable> type, String name) {
        this(type, name, false);
    }

}
