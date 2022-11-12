package com.csabamarko.springboot.jpa.graphqldemo1.common.utils;

import org.springframework.lang.NonNull;

public interface ClassUtils {

    static boolean isSameOrSubClass(@NonNull Class<?> testedClass, @NonNull Class<?> superClass) {
        return superClass.isAssignableFrom(testedClass);
    }

}
