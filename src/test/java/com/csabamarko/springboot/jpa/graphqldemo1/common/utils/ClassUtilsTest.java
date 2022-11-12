package com.csabamarko.springboot.jpa.graphqldemo1.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.csabamarko.springboot.jpa.graphqldemo1.common.utils.ClassUtils.isSameOrSubClass;

public class ClassUtilsTest {
    
    @Test
    void testIsSameOrSubClass_WithDummy() {
        Assertions.assertTrue(isSameOrSubClass(DummySub.class, DummyBase.class),
                "DummySub extends DummyBase");
        Assertions.assertTrue(isSameOrSubClass(DummySub.class, DummySub.class),
                "DummySub is DummySub");
        Assertions.assertTrue(isSameOrSubClass(DummySub.class, DummyInterface.class),
                "DummySub implements DummyInterface");

        Assertions.assertFalse(isSameOrSubClass(DummyBase.class, DummySub.class));
        Assertions.assertFalse(isSameOrSubClass(DummyInterface.class, DummySub.class));
    }

    private interface DummyInterface {
    }

    private static class DummyBase {
    }

    private static class DummySub extends DummyBase implements DummyInterface {
    }

}
