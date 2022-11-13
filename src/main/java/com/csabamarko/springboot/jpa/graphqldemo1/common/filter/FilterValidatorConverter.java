package com.csabamarko.springboot.jpa.graphqldemo1.common.filter;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator.OperatorLiteral;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.metadata.EntityFilterMetaData;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

// TODO make it non-static
public class FilterValidatorConverter {

    private static final String SIMPLE_FILTER_NAME = "filter";

    /*
        If the filter part in the query is like:
            filter: {fieldName: {equals: "LiteralStringValue1"}}
        Then the args Map is like:
            {"filter":
                {"fieldName":
                    {"equals": "LiteralStringValue1"}
                 }
             }
     */
    @SuppressWarnings("unchecked")
    public static <E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>>
    Optional<SimpleFilterModel<E, ID, F>> argsFilterMapToModel(@NonNull Class<E> entityClass,
                                                           @Nullable Map<String, Object> args) throws FilterException {
        if (args == null) {
            return Optional.empty();
        }
        if (! args.containsKey(SIMPLE_FILTER_NAME)) {
            return Optional.empty();
        }
        try {
            var filterMap = (Map<String, Object>) args.get(SIMPLE_FILTER_NAME);
            // There should be only one entity:
            if (filterMap.isEmpty()) {
                throw new FilterException("Invalid filter: Missing field/attribute selection");
            }
            return filterMapToModel(entityClass, filterMap);
        } catch (ClassCastException e) {
            throw new FilterException("Invalid filter: ", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends RootEntity<ID>, ID extends Serializable, F extends Comparable<F>>
    Optional<SimpleFilterModel<E, ID, F>> filterMapToModel(@NonNull Class<E> entityClass,
                                                           @NonNull Map<String, Object> filterMap)
            throws FilterException {
        var fieldMapEntry = getFirstAndOnlyEntry(filterMap);
        var potentialFieldName = fieldMapEntry.getKey();
        var fieldModel = getEntityAttributeInfo(entityClass, potentialFieldName)
                .orElseThrow(() -> new FilterException(String.format("Invalid simple filter: no such field or the field cannot be filtered: '%s'", potentialFieldName)));
        var operatorMap = (Map<String, Object>) fieldMapEntry.getValue();
        if (operatorMap.isEmpty()) {
            throw new FilterException("Invalid filter expression: no operator");
        }
        var opMapEntry = getFirstAndOnlyEntry(operatorMap);
        var potentialOpName = opMapEntry.getKey();
        var objectValue = opMapEntry.getValue();
        var operator = OperatorLiteral.fromString(potentialOpName);
        var fieldModelCast = (FieldModel<F>) fieldModel;
        var objectValueCast = (F) objectValue;
        var result = new SimpleFilterModel<>(entityClass, operator, fieldModelCast, objectValueCast);
        return Optional.of(result);
    }


    private static Map.Entry<String, Object> getFirstAndOnlyEntry(@NonNull Map<String, Object> map) {
        if (map.size() == 1) {
            return map.entrySet().iterator().next();
        }
        throw new FilterException("Invalid number of key-value pairs in this map; expected only one.");
    }

    private static <E extends RootEntity<ID>, ID extends Serializable>
    Optional<FieldModel<?>> getEntityAttributeInfo(@NonNull Class<E> entityClass, String potentialFieldName) {
        var entityMetaOption = EntityFilterMetaData.getForEntity(entityClass);
        if (entityMetaOption.isPresent()) {
            var fieldMetaOption = entityMetaOption.get().getFieldMetaOption(potentialFieldName);
            if (fieldMetaOption.isPresent()) {
                return fieldMetaOption.map(fieldMeta -> fieldMeta);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}
