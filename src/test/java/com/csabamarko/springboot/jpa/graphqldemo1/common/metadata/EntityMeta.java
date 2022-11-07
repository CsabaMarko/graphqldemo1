package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class EntityMeta<E extends RootEntity<ID>, ID extends Serializable> {
    private final Class<E> type;
    private final String name;
    private final Map<String, FieldMeta> fieldsByName;

//    public EntityMeta(Class<E> type, String name, Map<String, FieldMeta<F>> fieldsByName) {
//        this.type = type;
//        this.name = name;
//        this.fieldsByName = fieldsByName;
//    }
//
//    public EntityMeta(Class<E> type, String name, FieldMeta<F>[] fields) {
//        this.type = type;
//        this.name = name;
//        this.fieldsByName = new HashMap<>();
//        for (FieldMeta<F> fieldMeta : fields) {
//            this.fieldsByName.put(fieldMeta.getName(), fieldMeta);
//        }
//    }

    public EntityMeta(Class<E> type, FieldMeta[] fields) {
        this.type = type;
        this.name = type.getSimpleName();
        this.fieldsByName = new HashMap<>();
        for (FieldMeta fieldMeta : fields) {
            this.fieldsByName.put(fieldMeta.getName(), fieldMeta);
        }
    }

    public EntityMeta(Class<E> type, Collection<FieldMeta> fields) {
        this.type = type;
        this.name = type.getSimpleName();
        this.fieldsByName = new HashMap<>();
        for (FieldMeta fieldMeta : fields) {
            this.fieldsByName.put(fieldMeta.getName(), fieldMeta);
        }
    }

    public FieldMeta getFieldMeta(String fieldName) {
        return fieldsByName.get(fieldName);
    }
}
