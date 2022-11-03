package com.csabamarko.springboot.jpa.graphqldemo1.test;

import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.function.Consumer;

public class GraphQlAssertions {

    private final GraphQlTester.Response actual;

    private GraphQlAssertions(GraphQlTester.Response actual) {
        this.actual = actual;
    }

    public static GraphQlAssertions assertThat(GraphQlTester.Response actual) {
        return new GraphQlAssertions(actual);
    }

    @SuppressWarnings("squid:S2699")
    public void satisfies(Consumer<GraphQlTester.Response> assertion) {
        assertion.accept(actual);
    }
}
