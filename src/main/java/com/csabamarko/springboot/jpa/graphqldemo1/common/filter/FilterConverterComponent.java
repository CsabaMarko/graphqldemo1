package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@Component
public class FilterConverterComponent<E extends RootEntity<ID>, ID extends Serializable> {

    public Optional<SimpleFilterModel<E, ID, ? extends Comparable<?>>> argsMapToModel(
            @NonNull Class<E> entityClass, @Nullable Map<String, Object> args) throws FilterException {
        return FilterValidatorConverter.argsFilterMapToModel(entityClass, args).map(SimpleFilterModel::withoutF);
    }

}
