package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import com.csabamarko.springboot.jpa.graphqldemo1.snow.ChangeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

class EntityMetaTest {

    @Test
    <E extends RootEntity<ID>, ID extends Serializable>
    void testConsWithTypeParams() {
        FieldMeta<?> idField = FieldMeta.from(String.class, "id", true);
        FieldMeta<?> changeNumberField = FieldMeta.from(String.class, "changeNumber");
        FieldMeta<?> activeField = FieldMeta.from(Boolean.class, "active");
        FieldMeta<?> timeWorkedField = FieldMeta.from(Integer.class, "timeWorked");

        List<FieldMeta<?>> fieldMetaList = Arrays.asList(idField, changeNumberField, activeField, timeWorkedField);

        EntityMeta<E, ID> entityMeta = EntityMeta.from(ChangeRequest.class, fieldMetaList);
        Assertions.assertEquals(ChangeRequest.class.getCanonicalName(), entityMeta.getType().getCanonicalName());
        Assertions.assertEquals(String.class,
                entityMeta.getFieldMeta("id").getType());
        Assertions.assertTrue(entityMeta.getFieldMeta("id").isId());
        Assertions.assertEquals(String.class,
                entityMeta.getFieldMeta("changeNumber").getType());
        Assertions.assertFalse(entityMeta.getFieldMeta("changeNumber").isId());
        Assertions.assertEquals(Boolean.class,
                entityMeta.getFieldMeta("active").getType());
        Assertions.assertFalse(entityMeta.getFieldMeta("active").isId());
        Assertions.assertEquals(Integer.class,
                entityMeta.getFieldMeta("timeWorked").getType());
        Assertions.assertFalse(entityMeta.getFieldMeta("timeWorked").isId());
    }

    @Test
    void testConsWithTypeWildcards() {
        FieldMeta<?> idField = new FieldMeta<>(String.class, "id", true);
        FieldMeta<?> changeNumberField = new FieldMeta<>(String.class, "changeNumber");
        FieldMeta<?> activeField = new FieldMeta<>(Boolean.class, "active");
        FieldMeta<?> timeWorkedField = new FieldMeta<>(Integer.class, "timeWorked");
        List<FieldMeta<?>> fieldMetaList = Arrays.asList(idField, changeNumberField, activeField, timeWorkedField);
        EntityMeta<?, ?> entityMeta = EntityMeta.from(ChangeRequest.class, fieldMetaList);
        Assertions.assertEquals(ChangeRequest.class.getCanonicalName(), entityMeta.getType().getCanonicalName());
        Assertions.assertEquals(String.class.getCanonicalName(),
                entityMeta.getFieldMeta("changeNumber").getType().getCanonicalName());
    }

}
