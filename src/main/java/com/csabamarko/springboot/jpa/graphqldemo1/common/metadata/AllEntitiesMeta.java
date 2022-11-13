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
public class AllEntitiesMeta<E extends RootEntity<ID>, ID extends Serializable> {
    private Map<Class<E>, EntityMeta<E, ID>> metaEntitiesByClass;

    public AllEntitiesMeta() {
        this.metaEntitiesByClass = new HashMap<>();
    }

//    public Optional<EntityMeta<E, ID>> getEntityMetaT(@NonNull Class<E> runtimeEntityClass) {
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

    @SuppressWarnings("unchecked")
    public void addT(@NonNull EntityMeta<?, ?> entityMeta) {
        var clazz = (Class<E>) entityMeta.getType();
        var entityMetaCast = (EntityMeta<E, ID>) entityMeta;
        metaEntitiesByClass.put(clazz, entityMetaCast);
    }

    public void closeMap() {
        this.metaEntitiesByClass = ImmutableMap.copyOf(this.metaEntitiesByClass);
    }

    @SafeVarargs
    public static
    <E extends RootEntity<ID>, ID extends Serializable>
    AllEntitiesMeta<E, ID> from(EntityMeta<? extends RootEntity<?>, ?>... entityMetaValues) {
        AllEntitiesMeta<E, ID> allEntitiesMeta = new AllEntitiesMeta<>();
        for (EntityMeta<? extends RootEntity<?>, ?> entityMeta : entityMetaValues) {
            @SuppressWarnings("unchecked")
            EntityMeta<E, ID> em = (EntityMeta<E, ID>) entityMeta;
            allEntitiesMeta.add(em);
        }
        allEntitiesMeta.closeMap();
        return allEntitiesMeta;
    }
}
