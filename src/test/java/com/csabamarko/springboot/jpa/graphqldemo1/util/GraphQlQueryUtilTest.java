package com.csabamarko.springboot.jpa.graphqldemo1.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GraphQlQueryUtilTest {

    @Test
    void testWithPagingAndFieldList() {
        var queryName = "changeRequests";
        var page = 1;
        var size = 2;
        String[] fields = {"id", "number", "description"};
        var model = GraphQlQueryUtil.GraphQlQueryModel.builder().queryName(queryName).page(page)
                .size(size).fields(fields).build();
        Assertions.assertEquals(queryName, model.getQueryName());
        Assertions.assertEquals(page, model.getPage());
        Assertions.assertEquals(size, model.getSize());
        Assertions.assertNull(model.getFilter());
        var queryString = GraphQlQueryUtil.makeQueryString(model);
        var expectedFieldsString = String.join(", ", fields);
        var expectedQueryString = String.format("{ %s(page: %d, size: %d) {%s} }", queryName, page, size,
                expectedFieldsString);
        Assertions.assertEquals(expectedQueryString, queryString);
    }

    @Test
    void testWithFilterAndPagingAndFieldList() {
        var queryName = "changeRequests";
        var page = 1;
        var size = 2;
        String[] fields = {"id", "number", "description"};
        var fieldName = "number";
        var operator = "equals";
        var fieldValue = "ABCDE";
        var filterModel = GraphQlQueryUtil.SimpleFilterModel.builder().fieldName(fieldName)
                .operator(operator).value(fieldValue).build();
        var filterModelString = GraphQlQueryUtil.makeFilterString(filterModel);
        var expectedFilterString = String.format("filter: {%s: {%s: \"%s\"}}", fieldName, operator, fieldValue);
        Assertions.assertEquals(expectedFilterString, filterModelString);
        var model = GraphQlQueryUtil.GraphQlQueryModel.builder().queryName(queryName)
                .filter(filterModelString)
                .page(page).size(size).fields(fields).build();
        Assertions.assertEquals(queryName, model.getQueryName());
        Assertions.assertEquals(page, model.getPage());
        Assertions.assertEquals(size, model.getSize());
        Assertions.assertEquals(filterModelString, model.getFilter());
        var queryString = GraphQlQueryUtil.makeQueryString(model);
        var expectedFieldsString = String.join(", ", fields);
        var expectedQueryString = String.format("{ %s(%s, page: %d, size: %d) {%s} }", queryName, filterModelString,
                page, size, expectedFieldsString);
        Assertions.assertEquals(expectedQueryString, queryString);
    }

}
