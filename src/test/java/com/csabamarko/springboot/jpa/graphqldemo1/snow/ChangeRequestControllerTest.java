package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.GraphQlConfig;
import com.csabamarko.springboot.jpa.graphqldemo1.common.TestUtils;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.FilterConverterComponent;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.JpaSpecificationCreatorComponent;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.SimpleFilterModel;
import com.csabamarko.springboot.jpa.graphqldemo1.common.filter.operator.OperatorLiteral;
import com.csabamarko.springboot.jpa.graphqldemo1.common.metadata.FieldMeta;
import com.csabamarko.springboot.jpa.graphqldemo1.util.GraphQlQueryUtil;
import com.csabamarko.springboot.jpa.graphqldemo1.util.GraphQlQueryUtil.GraphQlQueryModel;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.test.tester.GraphQlTester;

import javax.persistence.criteria.Path;
import java.time.OffsetDateTime;
import java.util.Optional;

import static com.csabamarko.springboot.jpa.graphqldemo1.test.GraphQlAssertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(GraphQlConfig.class)
@GraphQlTest(ChangeRequestController.class)
public class ChangeRequestControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private ChangeRequestRepository repository;

    @MockBean
    private FilterConverterComponent<ChangeRequest, String> filterConverter;

    @MockBean
    private JpaSpecificationCreatorComponent<ChangeRequest, String> jpaSpecificationCreator;

    // TODO repeating code parts: some of these tests can be parametrized

    @Test
    void list_emptyResult() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.empty());

        var queryName = "changeRequests";
        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName).page(1)
                .size(2).build());

        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class).hasSize(0));
    }

    @Test
    void list_unfilteredResult() {
        var queryName = "changeRequests";
        var testCollection = TestUtils.pageOf(TestData.cr1, TestData.cr2, TestData.cr3);
        var page = 1;
        var size = 3;

        when(repository.findAll(any(Pageable.class))).thenReturn(testCollection);
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.empty());

        String queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName).page(page)
                .size(size).build());

        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class)
                .hasSize(testCollection.getSize()));
    }

    @Test
    void list_filteredMultiResults() {
        var queryName = "changeRequests";
        var testCollection = TestUtils.pageOf(TestData.cr4, TestData.cr5);
        var page = 1;
        var size = 5;

        when(repository.findAll(ArgumentMatchers.any(), any(Pageable.class))).thenReturn(testCollection);

        // filter: { shortDescription: { equals: "--" } }
        var filterValue = "--";
        var fieldName = "shortDescription";
        var operator = OperatorLiteral.EQUALS;
        var filter = GraphQlQueryUtil.SimpleFilterModel.builder()
                .fieldName(fieldName).operator(operator.getText()).value(filterValue).build();
        var filterString = GraphQlQueryUtil.makeFilterString(filter);
        var queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName)
                .filter(filterString)
                .page(page).size(size).build());
        var simpleFilterModel = makeFilterModel(operator, fieldName, filterValue);
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.of(simpleFilterModel));

        var specification = makeSpecification(fieldName, filterValue);
        when(jpaSpecificationCreator.create(any())).thenReturn(specification);

        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class)
                .hasSize(testCollection.getSize()));
    }

    @Test
    void list_filteredSingleResult() {
        var queryName = "changeRequests";
        var testCollection = TestUtils.pageOf(TestData.cr1);
        var page = 1;
        var size = 5;

        when(repository.findAll(ArgumentMatchers.any(), any(Pageable.class))).thenReturn(testCollection);

        // filter: { changeNumber: { equals: "CHG903001" } }
        var filterValue = "CHG903001";
        var fieldName = "changeNumber";
        var operator = OperatorLiteral.EQUALS;
        var filter = GraphQlQueryUtil.SimpleFilterModel.builder()
                .fieldName(fieldName).operator(operator.getText()).value(filterValue).build();
        var filterString = GraphQlQueryUtil.makeFilterString(filter);
        var queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName)
                .filter(filterString)
                .page(page).size(size).build());
        var simpleFilterModel = makeFilterModel(operator, fieldName, filterValue);
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.of(simpleFilterModel));

        var specification = makeSpecification(fieldName, filterValue);
        when(jpaSpecificationCreator.create(any())).thenReturn(specification);

        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class)
                .hasSize(testCollection.getSize()));
    }


    @Test
    void list_filteredNoResults() {
        var queryName = "changeRequests";
        var testCollection = Page.empty();
        var page = 1;
        var size = 5;

        when(repository.findAll(ArgumentMatchers.any(), any(Pageable.class))).thenReturn(Page.empty());

        // filter: { shortDescription: { equals: "XX**" } }
        var filterValue = "XX**";
        var fieldName = "shortDescription";
        var operator = OperatorLiteral.EQUALS;

        var filter = GraphQlQueryUtil.SimpleFilterModel.builder()
                .fieldName(fieldName).operator(operator.getText()).value(filterValue).build();
        var filterString = GraphQlQueryUtil.makeFilterString(filter);
        var queryString = GraphQlQueryUtil.makeQueryString(GraphQlQueryModel.builder().queryName(queryName)
                .filter(filterString)
                .page(page).size(size).build());
        var simpleFilterModel = makeFilterModel(operator, fieldName, filterValue);
        when(filterConverter.argsMapToModel(any(), any())).thenReturn(Optional.of(simpleFilterModel));

        var specification = makeSpecification(fieldName, filterValue);
        when(jpaSpecificationCreator.create(any())).thenReturn(specification);

        var response = executeQuery(queryString);

        assertThat(response).satisfies(result -> result.path(queryName).entityList(ChangeRequest.class)
                .hasSize(testCollection.getSize()));
    }

    private static <F extends Comparable<F>>
    SimpleFilterModel<ChangeRequest, String, ? extends Comparable<?>> makeFilterModel(OperatorLiteral operatorLiteral,
                                                                                      String fieldName, F value) {
        @SuppressWarnings("unchecked")
        Class<F> clazz = (Class<F>) value.getClass();
        var fieldModel = new FieldMeta<>(clazz, fieldName);
        return new SimpleFilterModel<>(ChangeRequest.class, operatorLiteral, fieldModel, value);
    }

    private static Specification<ChangeRequest> makeSpecification(String fieldName, String value) {
        return (root, query, builder) -> {
            final Path<String> path = root.get(fieldName);
            return builder.equal(path, value);
        };
    }

    private GraphQlTester.Response executeQuery(String query) {
        return TestUtils.executeQuery(graphQlTester, query);
    }

    private static class TestData {
        static final ChangeRequest cr1 = ChangeRequest.builder().id("CR1").changeNumber("CHG903001")
                .shortDescription("Short Description 1").description("Description 1").active(true).type("Normal")
                .assignedTo(UserControllerTest.TestData.u1).createdBy(UserControllerTest.TestData.u2)
                .requestedBy(UserControllerTest.TestData.u3)
                .workStart(OffsetDateTime.parse("2021-04-12T00:00:00Z"))
                .workEnd(OffsetDateTime.parse("2021-04-13T00:00:00Z"))
                .timeWorkedHours(5)
                .build();
        static final ChangeRequest cr2 = ChangeRequest.builder().id("CR2").changeNumber("CHG903002")
                .shortDescription("Short Description 2").description("Description").active(false).type("Standard")
                .createdBy(UserControllerTest.TestData.u2)
                .requestedBy(UserControllerTest.TestData.u3)
                .timeWorkedHours(2)
                .build();
        static final ChangeRequest cr3 = ChangeRequest.builder().id("CR3").changeNumber("CHG903003")
                .shortDescription("Short Description 3").description("Description").active(true).type("Emergency")
                .createdBy(UserControllerTest.TestData.u4)
                .requestedBy(UserControllerTest.TestData.u2)
                .timeWorkedHours(3)
                .build();

        static final ChangeRequest cr4 = ChangeRequest.builder().id("CR4").changeNumber("CHG903004")
                .shortDescription("--").description("--")
                .build();

        static final ChangeRequest cr5 = ChangeRequest.builder().id("CR5").changeNumber("CHG903005")
                .shortDescription("--")
                .build();
    }
}
