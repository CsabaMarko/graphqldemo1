package com.csabamarko.springboot.jpa.graphqldemo1.util;

import io.github.classgraph.*;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * <b>Set of utils to generate boilerplate code.</b>
 *
 * <p>
 * (Not a real test, but it uses the test-engine to run, but by default, it doesn't run with the regular tests,
 * because of the @EnabledIfSystemProperty trick.)
 * </p>
 */
@CommonsLog
public class ToGraphQlReflectionUtil {

    private static final String ROOT_PACKAGE_NAME = "com.csabamarko.springboot.jpa.graphqldemo1.snow";
    private static final String INDENT = "    ";
    private static final Map<String, String> fieldTypeMap = initFieldTypeMap();
    private static final Map<String, String> filterTypeMap = initFieldFilterMap();

    private final PrintStream printer = System.out;


    @Test
    @EnabledIfSystemProperty(named = "com.csabamarko.springboot.jpa.graphqldemo1.util.run_helper_utils",
            matches = "true")
    void graphQlSchemaGen() {
        // https://github.com/classgraph/classgraph/wiki/Code-examples
        // Scan the package for classes, interfaces etc.:
        try (ScanResult scanResult = new ClassGraph().acceptPackages(ROOT_PACKAGE_NAME).enableAllInfo().scan()) {
            // First look for complex IDs that have @Embeddable:
            ClassInfoList classInfoListEmbed = scanResult.getClassesWithAnnotation(Embeddable.class);
            printer.println("## Embeddable\n");
            this.classesLoop(classInfoListEmbed,
                    classInfo -> printer.printf("type %s {%n", classInfo.getSimpleName()),
                    fieldInfo -> this.javaFieldToGraphQlField(fieldInfo).ifPresent(s -> printer.println(INDENT + s)),
                    classInfo -> printer.println("}\n")
                );
            printer.println();

            // Second, look for classes that has @Entity:
            ClassInfoList classInfoListEntities = scanResult.getClassesWithAnnotation(Entity.class);
            printer.println("## Entity\n");
            this.classesLoop(classInfoListEntities,
                    classInfo -> printer.printf("type %s {%n", classInfo.getSimpleName()),
                    fieldInfo -> this.javaFieldToGraphQlField(fieldInfo).ifPresent(s -> printer.println(INDENT + s)),
                    classInfo -> printer.println("}\n")
            );
            printer.println();
        }
    }

    @Test
    @EnabledIfSystemProperty(named = "com.csabamarko.springboot.jpa.graphqldemo1.util.run_helper_utils",
            matches = "true")
    void graphQlFilterGen() {
        try (ScanResult scanResult = new ClassGraph().acceptPackages(ROOT_PACKAGE_NAME).enableAllInfo().scan()) {
            ClassInfoList classInfoListEntities = scanResult.getClassesWithAnnotation(Entity.class);
            printer.println("## Input types for filter\n");
            this.classesLoop(classInfoListEntities,
                    classInfo -> printer.printf("input %s {%n", classInfo.getSimpleName() + "Filter"),
                    fieldInfo -> this.javaFieldToFilterField(fieldInfo).ifPresent(s -> printer.println(INDENT + s)),
                    classInfo -> printer.println("}\n")
            );
            printer.println();
        }
    }

    private static Map<String, String> initFieldTypeMap() {
        // http://www.graphql-java.com/documentation/scalars/
        // New scalars must be added in GraphQlConfig
        Map<String, String> m = new HashMap<>();
        m.put("java.lang.String", "String");
        m.put("java.lang.Integer", "Int");
        m.put("java.lang.Long", "Int");
        m.put("java.lang.Boolean", "Boolean");
        m.put("java.time.OffsetDateTime", "DateTime");
        m.put("java.math.BigDecimal", "BigDecimal");
        m.put("java.math.BigInteger", "BigInteger");
        return m;
    }

    private static Map<String, String> initFieldFilterMap() {
        // http://www.graphql-java.com/documentation/scalars/
        // New scalars must be added in GraphQlConfig
        Map<String, String> m = new HashMap<>();
        m.put("java.lang.String", "String");
        m.put("java.lang.Integer", "Int");
        m.put("java.lang.Long", "Int");
        m.put("java.lang.Boolean", "Boolean");
//        m.put("java.time.OffsetDateTime", "DateTime");
//        m.put("java.math.BigDecimal", "BigDecimal");
//        m.put("java.math.BigInteger", "BigInteger");
        return m;
    }

    private void classesLoop(@NonNull ClassInfoList classInfoList, @NonNull Consumer<ClassInfo> preFieldsAction,
                             @NonNull Consumer<FieldInfo> fieldAction, @NonNull Consumer<ClassInfo> postFieldsAction) {
        for (ClassInfo classInfo : classInfoList) {
            preFieldsAction.accept(classInfo);
            for (FieldInfo fieldInfo : classInfo.getFieldInfo()) {
                fieldAction.accept(fieldInfo);
            }
            postFieldsAction.accept(classInfo);
        }
    }

    private String formatGqlFieldLine(String fieldName, String fieldType, boolean addNonNullMark) {
        if (addNonNullMark) {
            return String.format("%s: %s!", fieldName, fieldType);
        }
        return String.format("%s: %s", fieldName, fieldType);
    }

    private Optional<String> javaFieldToGraphQlField(@NonNull FieldInfo fieldInfo) {
        var snPrefix = ROOT_PACKAGE_NAME + ".";
        var javaFieldName = fieldInfo.getName();
        final TypeSignature fieldType = fieldInfo.getTypeSignatureOrTypeDescriptor();
        var fieldTypeStr = fieldType.toString();
        boolean addNonNullMark = fieldInfo.hasAnnotation(javax.persistence.Id.class)
                || fieldInfo.hasAnnotation(EmbeddedId.class);
        if (fieldInfo.hasAnnotation(javax.persistence.Id.class)
                && fieldTypeStr.equals(String.class.getCanonicalName())) {
            return Optional.of(this.formatGqlFieldLine(javaFieldName, "ID", addNonNullMark));
        }
        if (fieldTypeMap.containsKey(fieldTypeStr)) {
            return Optional.of(this.formatGqlFieldLine(javaFieldName, fieldTypeMap.get(fieldTypeStr), addNonNullMark));
        }
        // Assume that type/classes from the same package are OK (relations, enums etc.):
        if (fieldTypeStr.startsWith(snPrefix)) {
            return Optional.of(this.formatGqlFieldLine(javaFieldName, fieldType.toStringWithSimpleNames(),
                    addNonNullMark));
        }
        return unrecognizedTypeMessage(fieldInfo);

    }

    private Optional<String> javaFieldToFilterField(@NonNull FieldInfo fieldInfo) {
        var snPrefix = ROOT_PACKAGE_NAME + ".";
        var javaFieldName = fieldInfo.getName();
        final TypeSignature fieldType = fieldInfo.getTypeSignatureOrTypeDescriptor();
        var fieldTypeStr = fieldType.toString();
        if (filterTypeMap.containsKey(fieldTypeStr)) {
            return Optional.of(
                    this.formatGqlFieldLine(javaFieldName, filterTypeMap.get(fieldTypeStr) + "Expression", false)
            );
        }
        // Assume (complex) types in this package may be skipped without a warning:
        if (fieldTypeStr.startsWith(snPrefix)) {
            return Optional.empty();
        }
        return unrecognizedTypeMessage(fieldInfo);
    }

    private Optional<String> unrecognizedTypeMessage(@NonNull FieldInfo fieldInfo) {
//        log.warn("## I don't recognize this java field/type: " + fieldInfo);
//        return Optional.empty();
        return Optional.of(String.format("## I don't recognize this java field/type: %s %s",
                fieldInfo.getTypeSignatureOrTypeDescriptor().toString(), fieldInfo.getName()));
    }

}
