package com.csabamarko.springboot.jpa.graphqldemo1.common;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterConverterComponent;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.JpaSpecificationCreatorComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

public abstract class ControllerBase<E extends RootEntity<ID>, ID extends Serializable> {

    protected static final int MAX_PAGE_SIZE = 100;

    protected final RepositoryBase<E, ID> repository;
    protected final FilterConverterComponent<E, ID> filterConverter;
    protected final JpaSpecificationCreatorComponent<E, ID> jpaSpecificationCreator;

    protected ControllerBase(RepositoryBase<E, ID> repository, FilterConverterComponent<E, ID> filterConverter,
                             JpaSpecificationCreatorComponent<E, ID> jpaSpecificationCreator) {
        this.repository = repository;
        this.filterConverter = filterConverter;
        this.jpaSpecificationCreator = jpaSpecificationCreator;
    }

    protected Page<E> list(int page, int size) {
        return repository.findAll(PageRequest.of(page - 1, Math.min(size, MAX_PAGE_SIZE)));
    }

    protected Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    protected Page<E> simpleFilteredList(@NonNull Class<E> runtimeEntityClass, @Nullable Map<String, Object> args,
                                         int page, int size) {
        var pageRequest = PageRequest.of(page - 1, Math.min(size, MAX_PAGE_SIZE));
        var filterModelOption = filterConverter.argsMapToModel(runtimeEntityClass, args);
        if (filterModelOption.isEmpty()) {
            // No real filter, so return unfiltered:
            return repository.findAll(pageRequest);
        }
        var specification = jpaSpecificationCreator.create(filterModelOption.get());
        return repository.findAll(specification, pageRequest);
    }

}
