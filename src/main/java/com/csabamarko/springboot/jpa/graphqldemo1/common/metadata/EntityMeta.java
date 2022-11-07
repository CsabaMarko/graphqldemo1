package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Getter
public class EntityMeta<E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>> {
    private final Class<E> type;
    private final String name;
    private final Map<String, FieldMeta<F>> fieldsByName;

    public EntityMeta(Class<E> type, Collection<FieldMeta<F>> fields) {
        this.type = type;
        this.name = type.getSimpleName();
        this.fieldsByName = new HashMap<>();
        for (FieldMeta<F> fieldMeta : fields) {
            this.fieldsByName.put(fieldMeta.getName(), fieldMeta);
        }
    }

    public FieldMeta<F> getFieldMeta(String fieldName) {
        return fieldsByName.get(fieldName);
    }

    public static
    <E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>>
    EntityMeta<E, ID, F> from(Class<?> type, Collection<FieldMeta<?>> fields) {
        @SuppressWarnings("unchecked")
        Class<E> typeCasted = (Class<E>) type;
        List<FieldMeta<F>> newFields = new ArrayList<>();
        for (FieldMeta<?> fieldMeta : fields) {
            @SuppressWarnings("unchecked")
            FieldMeta<F> newFM = (FieldMeta<F>) fieldMeta;
            newFields.add(newFM);
        }
        return new EntityMeta<>(typeCasted, newFields);
    }

}
