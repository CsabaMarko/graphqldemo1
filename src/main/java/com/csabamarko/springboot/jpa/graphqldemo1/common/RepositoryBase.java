package com.csabamarko.springboot.jpa.graphqldemo1.common;

import com.csabamarko.springboot.jpa.graphqldemo1.ReadOnlyPageableRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RepositoryBase<E, ID> extends ReadOnlyPageableRepository<E, ID>, JpaSpecificationExecutor<E> {
}
