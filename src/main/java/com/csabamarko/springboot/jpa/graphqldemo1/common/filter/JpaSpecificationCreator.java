package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator.OperatorLiteral;
import org.springframework.data.jpa.domain.Specification;


import javax.persistence.criteria.Path;
import java.io.Serializable;

public abstract class JpaSpecificationCreator {

    public static <E extends RootEntity<ID>, ID extends Serializable, VAL extends Comparable<VAL>>
    Specification<E> create(FieldModel<VAL> fieldModel, ValueModel<VAL> valueModel, OperatorLiteral operator) {
        return (root, query, builder) -> {
            final Path<VAL> path = root.<VAL>get(fieldModel.getName());
            final VAL value = valueModel.getValue();
            if (OperatorLiteral.EQ.equals(operator)) {
                return builder.equal(path, value);
            }
            if (OperatorLiteral.NE.equals(operator)) {
                return builder.notEqual(path, value);
            }
            if (OperatorLiteral.LT.equals(operator)) {
                return builder.lessThan(path, value);
            }
            if (OperatorLiteral.GT.equals(operator)) {
                return builder.greaterThan(path, value);
            }
            throw new FilterException("(Yet?) Unsupported operator: " + operator);
        };
    }

    public static <E extends RootEntity<ID>, ID extends Serializable, VAL extends Comparable<VAL>>
    Specification<E> create(SimpleFilterModel<E, ID, VAL> simpleFilterModel) {
        var valueModel = new SimpleValueModel<>(simpleFilterModel.getFieldModel().getType(), simpleFilterModel.getValue());
        return create(simpleFilterModel.getFieldModel(), valueModel, simpleFilterModel.getOperator());
    }

}
