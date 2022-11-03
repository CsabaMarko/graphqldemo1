package com.csabamarko.springboot.jpa.graphqldemo1.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.lang.NonNull;

import java.util.Arrays;

public abstract class TestUtils {

    public static GraphQlTester.Response executeQuery(@NonNull GraphQlTester graphQlTester, @NonNull String query) {
        return graphQlTester.document(query).execute();
    }

    @SafeVarargs
    public static <T> Page<T> pageOf(T... elements) {
        return new PageImpl<>(Arrays.asList(elements));
    }

}
