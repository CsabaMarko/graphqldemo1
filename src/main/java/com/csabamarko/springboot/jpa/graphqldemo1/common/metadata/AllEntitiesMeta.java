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
public class AllEntitiesMeta<E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>> {
    private Map<Class<E>, EntityMeta<E, ID, F>> metaEntitiesByClass;

    public AllEntitiesMeta() {
        this.metaEntitiesByClass = new HashMap<>();
    }

    public Optional<EntityMeta<E, ID, F>> getEntityMetaT(@NonNull Class<E> runtimeEntityClass) {
        if (metaEntitiesByClass.containsKey(runtimeEntityClass)) {
            return Optional.of(metaEntitiesByClass.get(runtimeEntityClass));
        }
        return Optional.empty();
    }

    public Optional<EntityMeta<E, ID, F>> getEntityMeta(@NonNull Class<?> runtimeEntityClass) {
        if (metaEntitiesByClass.containsKey(runtimeEntityClass)) {
            return Optional.of(metaEntitiesByClass.get(runtimeEntityClass));
        }
        return Optional.empty();
    }

    public void add(@NonNull EntityMeta<E, ID, F> entityMeta) {
        metaEntitiesByClass.put(entityMeta.getType(), entityMeta);
    }

    public void closeMap() {
        this.metaEntitiesByClass = ImmutableMap.copyOf(this.metaEntitiesByClass);
    }

    public static
    <E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>>
    AllEntitiesMeta<E, ID, F> from(EntityMeta<? extends RootEntity<?>, ?, ?>... entityMetaValues) {
        AllEntitiesMeta<E, ID, F> allEntitiesMeta = new AllEntitiesMeta<>();
        for (EntityMeta<? extends RootEntity<?>, ?, ?> entityMeta : entityMetaValues) {
            @SuppressWarnings("unchecked")
            EntityMeta<E, ID, F> em = (EntityMeta<E, ID, F>) entityMeta;
            allEntitiesMeta.add(em);
        }
        allEntitiesMeta.closeMap();
        return allEntitiesMeta;
    }
}
