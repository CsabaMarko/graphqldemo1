package com.csabamarko.springboot.jpa.graphqldemo1.common.metadata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.csabamarko.springboot.jpa.graphqldemo1.common.utils.ClassUtils.isSameOrSubClass;


public class FieldMetaTest {

    @Test
    void testStringId() {
        var fieldName = "id";
        FieldMeta<String> fieldMeta = new FieldMeta<>(String.class, fieldName, true);
        Assertions.assertEquals(String.class, fieldMeta.getType());
        Assertions.assertEquals(fieldName, fieldMeta.getName());
        Assertions.assertTrue(fieldMeta.isId());
        Assertions.assertTrue(fieldMeta.getType().isAssignableFrom(String.class));
        Assertions.assertTrue(isSameOrSubClass(fieldMeta.getType(), Comparable.class));
    }

    @Test
    void testInteger() {
        var fieldName = "timeWorked";
        var fieldMeta = FieldMeta.from(Integer.class, fieldName);
        Assertions.assertEquals(Integer.class, fieldMeta.getType());
        Assertions.assertEquals(fieldName, fieldMeta.getName());
        Assertions.assertFalse(fieldMeta.isId());
        Assertions.assertTrue(fieldMeta.getType().isAssignableFrom(Integer.class));
        Assertions.assertTrue(isSameOrSubClass(fieldMeta.getType(), Comparable.class));
    }

}
