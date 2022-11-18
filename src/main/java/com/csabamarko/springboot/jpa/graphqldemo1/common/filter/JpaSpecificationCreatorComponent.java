package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class JpaSpecificationCreatorComponent<E extends RootEntity<ID>, ID extends Serializable> {

    public Specification<E> create(SimpleFilterModel<E, ID, ? extends Comparable<?>> simpleFilterModel) throws FilterException {
        return JpaSpecificationCreator.create(simpleFilterModel);
    }

}
