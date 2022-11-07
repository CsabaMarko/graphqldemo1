package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import graphql.com.google.common.collect.ImmutableMap;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public class AllEntities<E extends RootEntity<ID>, ID extends Serializable, F extends Serializable> {
    private Map<Class<E>, EntityMeta<E, ID>> metaEntitiesByClass;

    public AllEntities() {
        this.metaEntitiesByClass = new HashMap<>();
    }

//    public Optional<EntityMeta<E, ID>> getEntityMeta(@NonNull Class<E> runtimeEntityClass) {
//        if (metaEntitiesByClass.containsKey(runtimeEntityClass)) {
//            return Optional.of(metaEntitiesByClass.get(runtimeEntityClass));
//        }
//        return Optional.empty();
//    }

    public Optional<EntityMeta<E, ID>> getEntityMeta(@NonNull Class<?> runtimeEntityClass) {
        if (metaEntitiesByClass.containsKey(runtimeEntityClass)) {
            return Optional.of(metaEntitiesByClass.get(runtimeEntityClass));
        }
        return Optional.empty();
    }

    public void add(@NonNull EntityMeta<E, ID> entityMeta) {
        metaEntitiesByClass.put(entityMeta.getType(), entityMeta);
    }

    public void closeMap() {
        this.metaEntitiesByClass = ImmutableMap.copyOf(this.metaEntitiesByClass);
    }
}
