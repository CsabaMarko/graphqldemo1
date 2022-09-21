package com.csabamarko.springboot.jpa.graphqldemo1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyPageableRepository<T, ID>
        extends Repository<T, ID> {

    Iterable<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    Optional<T> findById(ID id);

    boolean existsById(ID id);

    long count();

}
