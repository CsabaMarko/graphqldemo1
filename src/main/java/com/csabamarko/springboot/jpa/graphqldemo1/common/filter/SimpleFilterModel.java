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
    F value;


    /**
     * XXX <br>
     * I have no idea why, but sometimes (this) Java doesn't allow to
     * replace the &lt;F&gt; type param with just &lt;? extends Comparable&lt;?&gt;&gt;.
     * Especially when I put this type into an Optional&lt;&gt;.
     * This is an acceptable workaround.
     */
    public SimpleFilterModel<E, ID, ? extends Comparable<?>> withoutF() {
        return this;
    }
}
