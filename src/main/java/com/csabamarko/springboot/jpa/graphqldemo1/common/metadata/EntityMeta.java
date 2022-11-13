package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;

@Getter
public class EntityMeta<E extends RootEntity<ID>, ID extends Serializable> {
    private final Class<E> type;
    private final String name;
    private final Map<String, FieldMeta<? extends Comparable<?>>> fieldsByName;

    public EntityMeta(Class<E> type, Collection<FieldMeta<? extends Comparable<?>>> fields) {
        this.type = type;
        this.name = type.getSimpleName();
        this.fieldsByName = new HashMap<>();
        for (FieldMeta<?> fieldMeta : fields) {
            this.fieldsByName.put(fieldMeta.getName(), fieldMeta);
        }
    }

    public FieldMeta<? extends Comparable<?>> getFieldMeta(String fieldName) {
        return fieldsByName.get(fieldName);
    }

    public Optional<FieldMeta<? extends Comparable<?>>> getFieldMetaOption(String fieldName) {
        return Optional.ofNullable(fieldsByName.get(fieldName));
    }

    public static
    <E extends RootEntity<ID>, ID extends Serializable>
    EntityMeta<E, ID> from(Class<? extends RootEntity<?>> type,
                           Collection<FieldMeta<? extends Comparable<?>>> fields) {
        @SuppressWarnings("unchecked")
        Class<E> typeCasted = (Class<E>) type;
        List<FieldMeta<?>> newFields = new ArrayList<>(fields);
        return new EntityMeta<>(typeCasted, newFields);
    }

}
