package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator.OperatorLiteral;
import lombok.Value;

import java.io.Serializable;

@Value
public class SimpleFilterModel<E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>> {

    Class<E> entityClass;
    OperatorLiteral operator;
    FieldModel<F> fieldModel;
    // ValueModel<F> valueModel;
    F value;

}
