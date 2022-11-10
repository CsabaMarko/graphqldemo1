package com.csabamarko.springboot.jpa.graphqldemo1.common;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterValidatorConverter;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.JpaSpecificationCreator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public abstract class ControllerBase<E extends RootEntity<ID>, ID extends Serializable> {

    protected static final int MAX_PAGE_SIZE = 100;

    protected final RepositoryBase<E, ID> repository;

    protected ControllerBase(RepositoryBase<E, ID> repository) {
        this.repository = repository;
    }

    protected Page<E> list(int page, int size) {
        return repository.findAll(PageRequest.of(page - 1, Math.min(size, MAX_PAGE_SIZE)));
    }

    protected Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    protected Page<E> simpleFilteredlist(@NonNull Class<E> runtimeEntityClass, Map<String, Object> args, int page,
                                         int size) {
        var filterModelOption = FilterValidatorConverter
                .argsFilterMapToModel(runtimeEntityClass, args);
        var pageRequest = PageRequest.of(page - 1, Math.min(size, MAX_PAGE_SIZE));
        if (filterModelOption.isEmpty()) {
            // No real filter, so return unfiltered:
            return repository.findAll(pageRequest);
        }
        var specification = JpaSpecificationCreator.create(filterModelOption.get());
        return repository.findAll(specification, pageRequest);
    }

}
