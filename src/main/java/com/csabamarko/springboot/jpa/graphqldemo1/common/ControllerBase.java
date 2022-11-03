package com.csabamarko.springboot.jpa.graphqldemo1.common;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
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

}
