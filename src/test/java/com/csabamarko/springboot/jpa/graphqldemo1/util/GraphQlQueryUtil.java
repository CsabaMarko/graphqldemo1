package com.csabamarko.springboot.jpa.graphqldemo1.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class GraphQlQueryUtil {

    private static final String QUERY_TEMPLATE_MAIN = "{ %s%s {%s} }";
    private static final String FILTER_TEMPLATE = "filter: {%s: {%s: %s}}";
    private static final String QUOTE = "\"";
    private static final String ESCAPED_QUOTE = "\\\"";

    @Getter
    @Setter
    @Builder
    public static class GraphQlQueryModel {
        private String queryName;
        private String filter;
        private Integer page;
        private Integer size;
        private String[] fields;
    }

    @Getter
    @Setter
    @Builder
    public static class SimpleFilterModel {
        private String fieldName;
        private String operator;
        private Object value;
    }

    public static String makeQueryString(@NonNull GraphQlQueryModel model) {
        return String.format(QUERY_TEMPLATE_MAIN, model.getQueryName(), makeParams(model),
                makeFields(model.getFields()));
    }

    public static String makeFilterString(@NonNull SimpleFilterModel simpleFilterModel) {
        return String.format(FILTER_TEMPLATE, simpleFilterModel.getFieldName(), simpleFilterModel.getOperator(),
                makeValueString(simpleFilterModel.getValue()));
    }

    private static String makeValueString(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof String) {
            return QUOTE + ((String ) value).replace(QUOTE, ESCAPED_QUOTE) + QUOTE;
        }
        if (value instanceof Number) {
            return value.toString();
        }
        throw new IllegalArgumentException("Unrecognized type/class for argument 'value': "
                + value.getClass().getCanonicalName());
    }

    private static String makeParams(@NonNull GraphQlQueryModel model) {
        List<String> list = new ArrayList<>();
        if (model.getFilter() != null) {
            list.add(model.getFilter());
        }
        if (model.getPage() != null) {
            list.add(String.format("page: %d", model.getPage()));
        }
        if (model.getSize() != null) {
            list.add(String.format("size: %d", model.getSize()));
        }
        if (list.isEmpty()) {
            return "";
        }
        return "(" + String.join(", ", list) + ")";
    }

    private static String makeFields(String[] fields) {
        if (fields == null || fields.length == 0) {
            // GraphQl doesn't allow empty field-list
            return "id";
        }
        return String.join(", ", fields);
    }

}
