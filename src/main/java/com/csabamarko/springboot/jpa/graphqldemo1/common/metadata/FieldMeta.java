package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import lombok.Getter;

@Getter
public class FieldMeta<F extends Comparable<F>> {
    private final Class<F> type;
    private final String name;
    private final boolean id;

    public FieldMeta(Class<F> type, String name, boolean id) {
        this.type = type;
        this.name = name;
        this.id = id;
    }

    public FieldMeta(Class<F> type, String name) {
        this(type, name, false);
    }

    public Class<? extends Comparable<?>> getTypeU() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public static <F extends Comparable<F>>
    FieldMeta<F> from(Class<? extends Comparable<?>> type, String name, boolean id) {
        return new FieldMeta<>((Class<F>)type, name, id);
    }

    public static <F extends Comparable<F>>
    FieldMeta<F> from(Class<? extends Comparable<?>> type, String name) {
        return FieldMeta.from(type, name, false);
    }
}
