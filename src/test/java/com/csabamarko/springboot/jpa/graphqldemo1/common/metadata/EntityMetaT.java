package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class EntityMetaT<E extends RootEntity<ID>, ID extends Serializable> {
    private final Class<E> type;
    private final String name;
    private final Map<String, FieldMetaT<?>> fieldsByName;

    public EntityMetaT(Class<E> type, Collection<FieldMetaT<?>> fields) {
        this.type = type;
        this.name = type.getSimpleName();
        this.fieldsByName = new HashMap<>();
        for (FieldMetaT<?> fieldMeta : fields) {
            this.fieldsByName.put(fieldMeta.getName(), fieldMeta);
        }
    }

    public FieldMetaT<?> getFieldMeta(String fieldName) {
        return fieldsByName.get(fieldName);
    }
}
